package com.jeferro.products.accounts.infrastructure.adapters.bcrypt

import com.jeferro.products.accounts.domain.services.PasswordEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class PasswordBcryptEncoder : PasswordEncoder {

    private val bCryptPasswordEncoder = BCryptPasswordEncoder()

    override fun matches(plainPassword: String, encodedPassword: String): Boolean {
        return bCryptPasswordEncoder.matches(plainPassword, encodedPassword)
    }
}