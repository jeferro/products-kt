package com.jeferro.lib.infrastructure.shared.security.services

import com.jeferro.lib.domain.models.auth.Auth
import com.jeferro.lib.infrastructure.shared.security.jwt.JwtEncoder
import com.jeferro.lib.infrastructure.shared.security.models.AuthRestUserDetails
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthRestService(
    val jwtEncoder: JwtEncoder
) {

    fun getAuthFromRequest(): Auth {
        val authUserDetails = SecurityContextHolder.getContext().authentication?.principal

        if (authUserDetails == null || authUserDetails !is AuthRestUserDetails) {
            return Auth.createOfAnonymous()
        }

        return Auth.createOfUser(
            authUserDetails.userId,
            authUserDetails.roles
        )
    }

    fun getAuthenticationToken(auth: Auth): String {
        return jwtEncoder.encode(auth)
    }
}