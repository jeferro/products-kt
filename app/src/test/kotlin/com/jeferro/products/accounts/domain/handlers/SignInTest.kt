package com.jeferro.products.accounts.domain.handlers

import com.jeferro.lib.domain.exceptions.UnauthorizedException
import com.jeferro.lib.domain.models.auth.Auth
import com.jeferro.products.accounts.domain.handlers.params.SignInParams
import com.jeferro.products.accounts.domain.models.AccountMother
import com.jeferro.products.accounts.domain.models.AccountMother.oneAccount
import com.jeferro.products.accounts.domain.models.UsernameMother.oneUsername
import com.jeferro.products.accounts.domain.repositories.FakeAccountRepository
import com.jeferro.products.accounts.domain.services.FakePasswordEncoder
import com.jeferro.products.shared.domain.models.auth.AuthMother.oneAnonymousAuth
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class SignInTest {

    companion object {
        const val ONE_USER_PASSWORD = "one-password"
    }

    private val passwordEncoder = FakePasswordEncoder(
        ONE_USER_PASSWORD to AccountMother.ONE_ACCOUNT_ENCRYPTED_PASSWORD
    )

    private val userCredentialsRepository = FakeAccountRepository()

    private val signIn = SignIn(
        userCredentialsRepository,
        passwordEncoder
    )

    @Test
    fun `should sign in user`() = runTest {
        val account = oneAccount()
        userCredentialsRepository.reset(account)

        val params = SignInParams(
            oneAnonymousAuth(),
            account.username,
            ONE_USER_PASSWORD
        )

        val auth = signIn.execute(params)

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
        userCredentialsRepository.reset(account)

        val params = SignInParams(
            oneAnonymousAuth(),
            account.username,
            "one-user-wrong-password"
        )

        assertThrows<UnauthorizedException> {
            signIn.execute(params)
        }
    }

    @Test
    fun `should fail when user doesn't exist`() = runTest {
        userCredentialsRepository.clear()

        val params = SignInParams(
            oneAnonymousAuth(),
            oneUsername(),
            ONE_USER_PASSWORD
        )

        assertThrows<UnauthorizedException> {
            signIn.execute(params)
        }
    }

}

