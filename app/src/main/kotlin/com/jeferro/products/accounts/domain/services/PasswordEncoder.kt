package com.jeferro.products.accounts.domain.services

interface PasswordEncoder {

    fun matches(plainPassword: String, encodedPassword: String): Boolean
}
