package com.jeferro.products.infrastructure.authentications.adapters.bcrypt

import com.jeferro.products.domain.authentications.services.PasswordEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class PasswordBcryptEncoder : PasswordEncoder {

    private val bCryptPasswordEncoder = BCryptPasswordEncoder()

    override fun matches(plainPassword: String, encodedPassword: String): Boolean {
        return bCryptPasswordEncoder.matches(plainPassword, encodedPassword)
    }
}