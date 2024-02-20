package com.jeferro.lib.domain.models.auth

import com.jeferro.lib.domain.exceptions.ForbiddenException
import com.jeferro.lib.domain.exceptions.UnauthorizedException
import com.jeferro.lib.domain.models.values.Value
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE

class Auth(
    val type: AuthType,
    val userId: UserId?,
    val roles: List<String>
) : Value() {

    companion object {
        fun createOfUser(
            userId: UserId,
            roles: List<String>
        ): Auth {
            return Auth(
                AuthType.USER,
                userId,
                roles
            )
        }

        fun createOfSystem(): Auth {
            return Auth(
                AuthType.SYSTEM,
                null,
                emptyList()
            )
        }

        fun createOfAnonymous(): Auth {
            return Auth(
                AuthType.ANONYMOUS,
                null,
                emptyList()
            )
        }
    }

    private val isAnonymous = (type == AuthType.ANONYMOUS)

    private val isSystem = (type == AuthType.SYSTEM)

    fun ensurePermissions(mandatoryRoles: List<String>) {
        if (isSystem) {
            return
        }

        if (mandatoryRoles.isEmpty()) {
            return
        }

        if (isAnonymous) {
            throw UnauthorizedException.create()
        }

        if (!roles.containsAll(mandatoryRoles)) {
            throw ForbiddenException.createOf(this)
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