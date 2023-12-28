package com.jeferro.products.domain.shared.handlers.params

import com.jeferro.products.domain.shared.entities.auth.Auth
import com.jeferro.products.domain.shared.entities.auth.UserId
import com.jeferro.products.domain.shared.entities.values.Value
import com.jeferro.products.domain.shared.exceptions.UnauthorizedException.Companion.unauthorizedExceptionOf

abstract class Params<R>(
    val auth: Auth
) : Value() {

    val userId: UserId
        get() {
            if (auth.userId == null) {
                throw unauthorizedExceptionOf()
            }

            return auth.userId
        }
}
