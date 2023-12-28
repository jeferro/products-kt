package com.jeferro.products.domain.authentications.services

interface PasswordEncoder {

    fun matches(plainPassword: String, encodedPassword: String): Boolean
}
