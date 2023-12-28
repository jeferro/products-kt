package com.jeferro.products.domain.authentications.entities

import com.jeferro.products.domain.shared.entities.auth.UserIdMother.oneUserId
import com.jeferro.products.domain.shared.entities.auth.UsernameMother.oneUsername
import com.jeferro.products.domain.shared.entities.metadata.MetadataMother.oneMetadata
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class UserCredentialsTest {

    @Nested
    inner class Constructor {

        @Test
        fun `should create user credential`() {
            val userId = oneUserId()
            val username = oneUsername()
            val encryptedPassword = "encrypted-password"
            val roles = listOf("USER")
            val metadata = oneMetadata()

            val userCredentials = UserCredentials(
                userId,
                username,
                encryptedPassword,
                roles,
                metadata
            )

            assertEquals(userId, userCredentials.id)
            assertEquals(username, userCredentials.username)
            assertEquals(encryptedPassword, userCredentials.encodedPassword)
            assertEquals(roles, userCredentials.roles)
            assertEquals(metadata, userCredentials.metadata)
        }
    }
}
