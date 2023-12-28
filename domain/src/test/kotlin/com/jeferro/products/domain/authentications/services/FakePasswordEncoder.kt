package com.jeferro.products.domain.authentications.services

class FakePasswordEncoder(
    vararg credentials: Pair<String, String>
) : PasswordEncoder {

    private val passwordPairs = credentials.toMap()

    override fun matches(plainPassword: String, encodedPassword: String): Boolean {
        return passwordPairs[plainPassword] == encodedPassword
    }
}