package com.jeferro.products.accounts.domain.models

import com.jeferro.lib.domain.exceptions.ValueValidationException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UsernameTest {

    @Nested
    inner class ConstructorTests {
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