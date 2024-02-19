package com.jeferro.products.shared.infrastructure.adapters.rest.dtos

data class ErrorRestDTO(
    val code: Code,
    val message: String
) {
    enum class Code(val value: String) {
        INTERNAL_ERROR("INTERNAL_ERROR"),
        VALUE_VALIDATION("VALUE_VALIDATION"),
        UNAUTHORIZED("UNAUTHORIZED"),
        FORBIDDEN("FORBIDDEN"),
        NOT_FOUND("NOT_FOUND")
    }

}
