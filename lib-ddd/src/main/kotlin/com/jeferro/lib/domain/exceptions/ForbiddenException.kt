package com.jeferro.lib.domain.exceptions

import com.jeferro.lib.domain.models.auth.Auth
import com.jeferro.lib.domain.models.auth.UserId

class ForbiddenException(
    message: String
) : DomainException(message, null) {

    companion object {
        fun createOf(auth: Auth): ForbiddenException {
            return ForbiddenException(
                "User '${auth.who}' doesn't have mandatory permissions"
            )
        }

        fun createOf(userId: UserId): ForbiddenException {
            return ForbiddenException(
                "User '${userId}' doesn't have mandatory permissions"
            )
        }
    }

}