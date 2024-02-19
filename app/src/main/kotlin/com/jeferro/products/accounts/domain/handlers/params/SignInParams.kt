package com.jeferro.products.accounts.domain.handlers.params

import com.jeferro.lib.domain.handlers.HandlerParams
import com.jeferro.lib.domain.models.auth.Auth
import com.jeferro.products.accounts.domain.models.Username

class SignInParams(
    auth: Auth,
    val username: Username,
    val password: String
) : HandlerParams<Auth>(auth) {

    override fun toString(): String {
        return "[ username = $username, password = ***]"
    }
}