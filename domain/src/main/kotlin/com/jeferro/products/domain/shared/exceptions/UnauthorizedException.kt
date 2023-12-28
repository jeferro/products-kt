package com.jeferro.products.domain.shared.exceptions

class UnauthorizedException(
    message: String
) : DomainException(message, null) {

    companion object {
        fun unauthorizedExceptionOf(): UnauthorizedException {
            return UnauthorizedException(
                "Unauthorized exception"
            )
        }
    }

}