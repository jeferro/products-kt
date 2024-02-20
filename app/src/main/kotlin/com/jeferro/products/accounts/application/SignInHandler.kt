package com.jeferro.products.accounts.application

import com.jeferro.lib.domain.exceptions.UnauthorizedException
import com.jeferro.lib.application.CommandHandler
import com.jeferro.lib.application.Handler
import com.jeferro.lib.domain.models.auth.Auth
import com.jeferro.products.accounts.application.operations.SignIn
import com.jeferro.products.accounts.domain.repositories.AccountsRepository
import com.jeferro.products.accounts.domain.services.PasswordEncoder

@Handler
class SignInHandler(
    private val accountsRepository: AccountsRepository,
    private val passwordEncoder: PasswordEncoder
) : CommandHandler<SignIn, Auth>() {

    override suspend fun handle(operation: SignIn): Auth {
        val username = operation.username

        val account = accountsRepository.findByUsername(username)
            ?: throw UnauthorizedException.create()

        val matches = passwordEncoder.matches(
            operation.password,
            account.encodedPassword
        )

        if (!matches) {
            throw UnauthorizedException.create()
        }

        return Auth.createOfUser(account.id, account.roles)
    }
}
