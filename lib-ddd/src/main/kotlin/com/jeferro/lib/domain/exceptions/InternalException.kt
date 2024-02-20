package com.jeferro.lib.domain.exceptions

open class InternalException(
    message: String,
    cause: Exception?
) : DomainException(message, cause) {

    companion object {
        fun create(cause: Exception): InternalException {
            return InternalException(
                cause.message ?: "Unknown error message",
                cause
            )
        }
    }

}