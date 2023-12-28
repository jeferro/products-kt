package com.jeferro.products.infrastructure.shared.security

import com.jeferro.products.domain.shared.entities.auth.Auth
import com.jeferro.products.domain.shared.entities.auth.Auth.Companion.authOfAnonymous
import com.jeferro.products.domain.shared.entities.auth.Auth.Companion.authOfUser
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthRestResolver {

    fun resolve(): Auth {
        val authUserDetails = SecurityContextHolder.getContext().authentication?.principal

        if (authUserDetails == null || authUserDetails !is AuthRestUserDetails) {
            return authOfAnonymous()
        }

        return authOfUser(
            authUserDetails.userId,
            authUserDetails.roles
        )
    }
}