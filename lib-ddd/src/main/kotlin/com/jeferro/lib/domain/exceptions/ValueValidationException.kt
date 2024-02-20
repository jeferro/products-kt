package com.jeferro.lib.domain.exceptions

class ValueValidationException(
    message: String
) : DomainException(message, null) {

    companion object {
        fun create(message: String): ValueValidationException {
            return ValueValidationException(message)
        }
    }
}
