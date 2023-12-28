package com.jeferro.products.domain.shared.exceptions

open class InternalException(
    message: String,
    cause: Exception?
) : DomainException(message, cause) {

    companion object {
        fun internalExceptionOf(cause: Exception): InternalException {
            return InternalException(
                cause.message ?: "Unknown error message",
                cause
            )
        }
    }

}