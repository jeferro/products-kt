package com.jeferro.products.domain.shared.entities.auth

import com.jeferro.products.domain.shared.entities.auth.UserId
import com.jeferro.products.domain.shared.exceptions.ValueValidationException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserIdTest {

    @Nested
    inner class Constructor {
        @Test
        fun `should create user id`() {
            val value = "one-user-id"

            val userId = UserId(value)

            assertEquals(value, userId.value)
        }

        @Test
        fun `should fail when value is empty`() {
            assertThrows<ValueValidationException> {
                UserId("")
            }
        }

        @Test
        fun `should fail when value is blank`() {
            assertThrows<ValueValidationException> {
                UserId(" ")
            }
        }
    }
}