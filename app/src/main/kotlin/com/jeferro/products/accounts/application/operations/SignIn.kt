package com.jeferro.products.accounts.application.operations

import com.jeferro.lib.application.operations.Operation
import com.jeferro.lib.domain.models.auth.Auth
import com.jeferro.products.accounts.domain.models.Username

class SignIn(
    auth: Auth,
    val username: Username,
    val password: String
) : Operation<Auth>(auth) {

    override fun toString(): String {
        return "[ username = $username, password = ***]"
    }
}