package com.jeferro.lib.application.operations

import com.jeferro.lib.domain.exceptions.UnauthorizedException
import com.jeferro.lib.domain.models.auth.Auth
import com.jeferro.lib.domain.models.auth.UserId
import com.jeferro.lib.domain.models.values.Value

abstract class Operation<R>(
    val auth: Auth
) : Value() {

    val authId: UserId
        get() {
            return auth.authId
                ?: throw UnauthorizedException.create()
        }
}
