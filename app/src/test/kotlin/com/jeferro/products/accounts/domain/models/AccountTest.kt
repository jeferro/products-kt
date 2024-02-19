package com.jeferro.products.accounts.domain.models

import com.jeferro.products.accounts.domain.models.UsernameMother.oneUsername
import com.jeferro.products.shared.domain.models.auth.UserIdMother.oneUserId
import com.jeferro.products.shared.domain.models.metadata.MetadataMother.oneMetadata
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class AccountTest {

    @Nested
    inner class Constructor {

        @Test
        fun `should create user credential`() {
            val userId = oneUserId()
            val username = oneUsername()
            val encryptedPassword = "encrypted-password"
            val roles = listOf("USER")
            val metadata = oneMetadata()

            val account = Account(
                userId,
                username,
                encryptedPassword,
                roles,
                metadata
            )

            assertEquals(userId, account.id)
            assertEquals(username, account.username)
            assertEquals(encryptedPassword, account.encodedPassword)
            assertEquals(roles, account.roles)
            assertEquals(metadata, account.metadata)
        }
    }
}
