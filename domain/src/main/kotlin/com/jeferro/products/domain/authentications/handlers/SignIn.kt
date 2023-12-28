package com.jeferro.products.domain.authentications.handlers

import com.jeferro.products.domain.authentications.handlers.params.SignInParams
import com.jeferro.products.domain.authentications.repositories.UserCredentialsRepository
import com.jeferro.products.domain.authentications.services.PasswordEncoder
import com.jeferro.products.domain.shared.entities.auth.Auth
import com.jeferro.products.domain.shared.entities.auth.Auth.Companion.authOfUser
import com.jeferro.products.domain.shared.exceptions.UnauthorizedException.Companion.unauthorizedExceptionOf
import com.jeferro.products.domain.shared.handlers.CommandHandler
import com.jeferro.products.domain.shared.handlers.Handler

@Handler
class SignIn(
    private val userCredentialsRepository: UserCredentialsRepository,
    private val passwordEncoder: PasswordEncoder
) : CommandHandler<SignInParams, Auth>() {

    override suspend fun handle(params: SignInParams): Auth {
        val username = params.username

        val userCredentials = userCredentialsRepository.findByUsername(username)
            ?: throw unauthorizedExceptionOf()

        val matches = passwordEncoder.matches(
            params.password,
            userCredentials.encodedPassword
        )

        if (!matches) {
            throw unauthorizedExceptionOf()
        }

        return authOfUser(userCredentials.id, userCredentials.roles)
    }
}
