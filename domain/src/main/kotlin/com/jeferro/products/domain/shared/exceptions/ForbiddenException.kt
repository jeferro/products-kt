package com.jeferro.products.domain.shared.exceptions

import com.jeferro.products.domain.shared.entities.auth.Auth
import com.jeferro.products.domain.shared.entities.auth.UserId

class ForbiddenException(
    message: String
) : DomainException(message, null) {

    companion object {
        fun forbiddenExceptionOf(auth: Auth): ForbiddenException {
            return ForbiddenException(
                "User '${auth.who}' doesn't have mandatory permissions"
            )
        }

        fun forbiddenExceptionOf(userId: UserId): ForbiddenException {
            return ForbiddenException(
                "User '${userId}' doesn't have mandatory permissions"
            )
        }
    }

}