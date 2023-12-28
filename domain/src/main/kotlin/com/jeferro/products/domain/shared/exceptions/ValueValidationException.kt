package com.jeferro.products.domain.shared.exceptions

class ValueValidationException(
    message: String
) : DomainException(message, null) {

    companion object {
        fun valueValidationExceptionOf(message: String): ValueValidationException {
            return ValueValidationException(message)
        }
    }
}
