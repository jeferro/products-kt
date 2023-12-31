package com.jeferro.products.domain.shared.entities.auth

import com.jeferro.products.domain.shared.exceptions.ValueValidationException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UsernameTest {

    @Nested
    inner class Constructor {
        @Test
        fun `should create username`() {
            val value = "username"

            val username = Username(value)

            assertEquals(value, username.value)
        }

        @Test
        fun `should fail when value is empty`() {
            assertThrows<ValueValidationException> {
                Username("")
            }
        }

        @Test
        fun `should fail when value is blank`() {
            assertThrows<ValueValidationException> {
                Username(" ")
            }
        }
    }
}