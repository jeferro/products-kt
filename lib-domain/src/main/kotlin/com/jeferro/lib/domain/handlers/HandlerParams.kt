package com.jeferro.lib.domain.handlers

import com.jeferro.lib.domain.exceptions.UnauthorizedException
import com.jeferro.lib.domain.models.auth.Auth
import com.jeferro.lib.domain.models.auth.UserId
import com.jeferro.lib.domain.models.values.Value

abstract class HandlerParams<R>(
    val auth: Auth
) : Value() {

    val userId: UserId
        get() {
            if (auth.userId == null) {
                throw UnauthorizedException.create()
            }

            return auth.userId
        }
}
