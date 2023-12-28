package com.jeferro.products.domain.shared.entities.auth

import com.jeferro.products.domain.Roles
import com.jeferro.products.domain.shared.entities.auth.Auth.Companion.authOfAnonymous
import com.jeferro.products.domain.shared.entities.auth.Auth.Companion.authOfSystem
import com.jeferro.products.domain.shared.entities.auth.Auth.Companion.authOfUser
import com.jeferro.products.domain.shared.entities.auth.AuthMother.oneAnonymousAuth
import com.jeferro.products.domain.shared.entities.auth.AuthMother.oneSystemAuth
import com.jeferro.products.domain.shared.entities.auth.AuthMother.oneUserAuth
import com.jeferro.products.domain.shared.entities.auth.AuthType.*
import com.jeferro.products.domain.shared.entities.auth.UserIdMother.oneUserId
import com.jeferro.products.domain.shared.exceptions.ForbiddenException
import com.jeferro.products.domain.shared.exceptions.UnauthorizedException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class AuthTest {

    @Nested
    inner class NamedConstructors {

        @Test
        fun `should create a anonymous auth`() {
            val auth = authOfAnonymous()

            assertEquals(ANONYMOUS, auth.type)
            assertNull(auth.userId)
            assertTrue(auth.roles.isEmpty())
            assertEquals("anonymous", auth.toString())
        }

        @Test
        fun `should create a system auth`() {
            val auth = authOfSystem()

            assertEquals(SYSTEM, auth.type)
            assertNull(auth.userId)
            assertTrue(auth.roles.isEmpty())
            assertEquals("system", auth.toString())
        }

        @Test
        fun `should create a user auth`() {
            val userId = oneUserId()
            val roles = listOf(Roles.USER, Roles.ADMIN)

            val auth = authOfUser(userId, roles)

            assertEquals(USER, auth.type)
            assertEquals(userId, auth.userId)
            assertEquals(roles, auth.roles)
        }
    }

    @Nested
    inner class EnsurePermissions {

        @Test
        fun `should grant permission to anonymous user if there aren't mandatory roles`() {
            val auth = oneAnonymousAuth()

            assertDoesNotThrow { auth.ensurePermissions(emptyList()) }
        }

        @Test
        fun `should deny permission to anonymous user if there are mandatory roles`() {
            val auth = oneAnonymousAuth()

            val mandatoryRoles = listOf(Roles.USER)

            assertThrows<UnauthorizedException> {
                auth.ensurePermissions(mandatoryRoles)
            }
        }

        @Test
        fun `should grant permission to system`() {
            val auth = oneSystemAuth()

            assertDoesNotThrow { auth.ensurePermissions(emptyList()) }
        }

        @Test
        fun `should grant permission to system when there are mandatory roles`() {
            val auth = oneSystemAuth()

            val mandatoryRoles = listOf(Roles.USER)
            assertDoesNotThrow { auth.ensurePermissions(mandatoryRoles) }
        }

        @Test
        fun `should grant permission to user if there aren't mandatory roles`() {
            val auth = authOfAnonymous()

            assertDoesNotThrow { auth.ensurePermissions(emptyList()) }
        }

        @Test
        fun `should grant permission to user if user has all mandatory permissions`() {
            val userRoles = listOf(Roles.USER)
            val auth = oneUserAuth(userRoles)

            assertDoesNotThrow { auth.ensurePermissions(userRoles) }
        }

        @Test
        fun `should deny permission to user if user hasn't all mandatory permissions`() {
            val userRoles = listOf(Roles.USER)
            val auth = oneUserAuth(userRoles)

            val mandatoryRoles = listOf(Roles.ADMIN)

            assertThrows<ForbiddenException> {
                auth.ensurePermissions(mandatoryRoles)
            }
        }
    }
}