package com.jeferro.products.accounts.infrastructure.adapters.rest.dtos

data class AuthRestDTO(
    val userId: String,
    val roles: List<String>
)
