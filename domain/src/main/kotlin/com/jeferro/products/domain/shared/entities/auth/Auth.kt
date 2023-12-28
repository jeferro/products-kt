package com.jeferro.products.domain.shared.entities.auth

import com.jeferro.products.domain.shared.entities.auth.AuthType.*
import com.jeferro.products.domain.shared.entities.values.Value
import com.jeferro.products.domain.shared.exceptions.ForbiddenException.Companion.forbiddenExceptionOf
import com.jeferro.products.domain.shared.exceptions.UnauthorizedException.Companion.unauthorizedExceptionOf
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE

class Auth(
    val type: AuthType,
    val userId: UserId?,
    val roles: List<String>
) : Value() {

    companion object {
        fun authOfUser(
            userId: UserId,
            roles: List<String>
        ): Auth {
            return Auth(
                USER,
                userId,
                roles
            )
        }

        fun authOfSystem(): Auth {
            return Auth(
                SYSTEM,
                null,
                emptyList()
            )
        }

        fun authOfAnonymous(): Auth {
            return Auth(
                ANONYMOUS,
                null,
                emptyList()
            )
        }
    }

    private val isAnonymous = (type == ANONYMOUS)

    private val isSystem = (type == SYSTEM)

    fun ensurePermissions(mandatoryRoles: List<String>) {
        if (isSystem) {
            return
        }

        if (mandatoryRoles.isEmpty()) {
            return
        }

        if (isAnonymous) {
            throw unauthorizedExceptionOf()
        }

        if (!roles.containsAll(mandatoryRoles)) {
            throw forbiddenExceptionOf(this)
        }
    }

    val who
        get(): String {
            if (isAnonymous) {
                return "anonymous"
            }

            if (isSystem) {
                return "system"
            }

            return userId!!.value
        }

    override fun toString(): String {
        if (isAnonymous || isSystem) {
            return who
        }

        return ToStringBuilder(this, SHORT_PREFIX_STYLE)
            .append("userId", userId)
            .append("roles", roles)
            .build()
    }
}