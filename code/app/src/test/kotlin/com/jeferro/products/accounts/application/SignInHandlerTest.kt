package com.jeferro.products.accounts.application

import com.jeferro.lib.domain.exceptions.UnauthorizedException
import com.jeferro.lib.domain.models.auth.Auth
import com.jeferro.products.accounts.application.operations.SignIn
import com.jeferro.products.accounts.domain.models.AccountMother
import com.jeferro.products.accounts.domain.models.AccountMother.oneAccount
import com.jeferro.products.accounts.domain.models.UsernameMother.oneUsername
import com.jeferro.products.accounts.domain.repositories.AccountsRepositoryInMemory
import com.jeferro.products.accounts.domain.services.FakePasswordEncoder
import com.jeferro.products.shared.domain.models.auth.AuthMother.oneAnonymousAuth
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class SignInHandlerTest {

    companion object {
        const val ONE_USER_PASSWORD = "one-password"
    }

    private val passwordEncoder = FakePasswordEncoder(
        ONE_USER_PASSWORD to AccountMother.ONE_ACCOUNT_ENCRYPTED_PASSWORD
    )

    private val accountsRepositoryInMemory = AccountsRepositoryInMemory()

    private val signInHandler = SignInHandler(
        accountsRepositoryInMemory,
        passwordEncoder
    )

    @Test
    fun `should sign in user`() = runTest {
        val account = oneAccount()
        accountsRepositoryInMemory.reset(account)

        val operation = SignIn(
            oneAnonymousAuth(),
            account.username,
            ONE_USER_PASSWORD
        )

        val auth = signInHandler.execute(operation)

        val expectedAuth = Auth.createOfUser(
            account.id,
            account.roles
        )

        assertAll(
            "check handler result",
            { assertEquals(expectedAuth, auth) }
        )
    }

    @Test
    fun `should fail when password is incorrect`() = runTest {
        val account = oneAccount()
        accountsRepositoryInMemory.reset(account)

        val operation = SignIn(
            oneAnonymousAuth(),
            account.username,
            "one-user-wrong-password"
        )

        assertThrows<UnauthorizedException> {
            signInHandler.execute(operation)
        }
    }

    @Test
    fun `should fail when user doesn't exist`() = runTest {
        accountsRepositoryInMemory.clear()

        val operation = SignIn(
            oneAnonymousAuth(),
            oneUsername(),
            ONE_USER_PASSWORD
        )

        assertThrows<UnauthorizedException> {
            signInHandler.execute(operation)
        }
    }

}

