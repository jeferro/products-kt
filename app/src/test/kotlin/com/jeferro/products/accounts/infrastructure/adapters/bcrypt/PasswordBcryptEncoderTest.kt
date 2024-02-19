package com.jeferro.products.accounts.infrastructure.adapters.bcrypt

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class PasswordBcryptEncoderTest {

    private val bCryptPasswordEncoder = BCryptPasswordEncoder()

    private val passwordBcryptEncoder = PasswordBcryptEncoder()

    @Test
    fun `should match when encoded password and plain password are correct`() {
        val plainPassword = "password"
        val encodedPassword = bCryptPasswordEncoder.encode(plainPassword)

        val result = passwordBcryptEncoder.matches(plainPassword, encodedPassword)

        assertTrue(result)
    }

    @Test
    fun `shouldn't match when encoded password and plain password are wrong`() {
        val plainPassword = "password"
        val wrongEncodedPassword = bCryptPasswordEncoder.encode("other-password")

        val result = passwordBcryptEncoder.matches(plainPassword, wrongEncodedPassword)

        assertFalse(result)
    }
}