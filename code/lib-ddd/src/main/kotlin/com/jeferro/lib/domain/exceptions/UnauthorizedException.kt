package com.jeferro.lib.domain.exceptions

class UnauthorizedException(
    message: String
) : DomainException(message, null) {

    companion object {
        fun create(): UnauthorizedException {
            return UnauthorizedException("Unauthorized exception")
        }
    }

}