package com.jeferro.products.domain.authentications.handlers.params

import com.jeferro.products.domain.shared.entities.auth.Auth
import com.jeferro.products.domain.shared.handlers.params.Params
import com.jeferro.products.domain.shared.entities.auth.Username
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE

class SignInParams(
    auth: Auth,
    val username: Username,
    val password: String
) : Params<Auth>(auth) {
    override fun toString(): String {
        return ToStringBuilder(this, SHORT_PREFIX_STYLE)
            .append("username", username)
            .append("password", "****")
            .build()
    }
}