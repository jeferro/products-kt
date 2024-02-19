package com.jeferro.lib.infrastructure.shared.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class JwtRequestFilter(
    private val jwtEncoder: JwtEncoder
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authUserDetails = jwtEncoder.decode(request)

        if (authUserDetails == null) {
            SecurityContextHolder.clearContext()

            filterChain.doFilter(request, response)
            return
        }

        SecurityContextHolder.getContext().authentication = createAuthenticationToken(request, authUserDetails)

        filterChain.doFilter(request, response)
    }

    private fun createAuthenticationToken(
        request: HttpServletRequest,
        userDetails: UserDetails
    ): UsernamePasswordAuthenticationToken {
        val authentication = UsernamePasswordAuthenticationToken.authenticated(
            userDetails,
            null,
            userDetails.authorities
        )

        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

        return authentication
    }
}
