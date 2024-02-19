package com.jeferro.products.accounts.domain.handlers

import com.jeferro.lib.domain.exceptions.UnauthorizedException
import com.jeferro.lib.domain.handlers.CommandHandler
import com.jeferro.lib.domain.handlers.Handler
import com.jeferro.lib.domain.models.auth.Auth
import com.jeferro.products.accounts.domain.handlers.params.SignInParams
import com.jeferro.products.accounts.domain.repositories.AccountsRepository
import com.jeferro.products.accounts.domain.services.PasswordEncoder

@Handler
class SignIn(
    private val accountsRepository: AccountsRepository,
    private val passwordEncoder: PasswordEncoder
) : CommandHandler<SignInParams, Auth>() {

    override suspend fun handle(params: SignInParams): Auth {
        val username = params.username

        val account = accountsRepository.findByUsername(username)
            ?: throw UnauthorizedException.create()

        val matches = passwordEncoder.matches(
            params.password,
            account.encodedPassword
        )

        if (!matches) {
            throw UnauthorizedException.create()
        }

        return Auth.createOfUser(account.id, account.roles)
    }
}
