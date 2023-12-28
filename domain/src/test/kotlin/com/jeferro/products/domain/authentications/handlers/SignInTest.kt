
package com.jeferro.products.domain.authentications.handlers

import com.jeferro.products.domain.authentications.entities.UserCredentialMother.ONE_USER_ENCRYPTED_PASSWORD
import com.jeferro.products.domain.authentications.entities.UserCredentialMother.oneUserCredential
import com.jeferro.products.domain.authentications.handlers.params.SignInParams
import com.jeferro.products.domain.authentications.repositories.UserCredentialInMemoryRepository
import com.jeferro.products.domain.authentications.services.FakePasswordEncoder
import com.jeferro.products.domain.shared.entities.auth.Auth.Companion.authOfUser
import com.jeferro.products.domain.shared.entities.auth.AuthMother.oneAnonymousAuth
import com.jeferro.products.domain.shared.exceptions.UnauthorizedException
import com.jeferro.products.domain.shared.services.logger.FakeLoggerCreator.Companion.configureFakeLogger
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class SignInTest {

    init {
        configureFakeLogger()
    }

    companion object {
        const val ONE_USER_PASSWORD = "one-password"
    }

    private val passwordEncoder = FakePasswordEncoder(
        ONE_USER_PASSWORD to ONE_USER_ENCRYPTED_PASSWORD
    )

    private val userCredentialsRepository = UserCredentialInMemoryRepository()

    private val signIn = SignIn(
        userCredentialsRepository,
        passwordEncoder
    )

    @Test
    fun `should sign in user`() = runTest {
        val oneUserCredential = oneUserCredential()

        userCredentialsRepository.reset(oneUserCredential)

        val params = SignInParams(
            oneAnonymousAuth(),
            oneUserCredential.username,
            ONE_USER_PASSWORD
        )

        val auth = signIn.execute(params)

        val expectedAuth = authOfUser(
            oneUserCredential.id,
            oneUserCredential.roles
        )

        assertAll(
            "check handler result",
            { assertEquals(expectedAuth, auth) }
        )
    }

    @Test
    fun `should fail when user doesn't exist`() = runTest {
        val oneUserCredential = oneUserCredential()

        userCredentialsRepository.clear()

        val params = SignInParams(
            oneAnonymousAuth(),
            oneUserCredential.username,
            ONE_USER_PASSWORD
        )

        assertThrows<UnauthorizedException> {
            signIn.execute(params)
        }
    }

    @Test
    fun `should fail when password is incorrect`() = runTest {
        val oneUserCredential = oneUserCredential()

        userCredentialsRepository.clear()

        val params = SignInParams(
            oneAnonymousAuth(),
            oneUserCredential.username,
            "one-user-wrong-password"
        )

        assertThrows<UnauthorizedException> {
            signIn.execute(params)
        }
    }

}

