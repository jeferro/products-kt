package com.jeferro.lib.domain.models.auth

import com.jeferro.lib.domain.exceptions.ForbiddenException
import com.jeferro.lib.domain.exceptions.UnauthorizedException
import com.jeferro.lib.domain.models.values.Value
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE

class Auth(
    val authId: UserId?,
    val roles: List<String>,
    private val isSystem: Boolean
) : Value() {

    companion object {
        fun createOfUser(
            userId: UserId,
            roles: List<String>
        ): Auth {
            return Auth(
                userId,
                roles,
                false
            )
        }

        fun createOfSystem(name: String): Auth {
            return Auth(
                UserId(name),
                emptyList(),
                true
            )
        }

        fun createOfAnonymous(): Auth {
            return Auth(
                null,
                emptyList(),
                false
            )
        }
    }

    private val isAnonymous = (authId == null)

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

            return authId!!.value
        }

    override fun toString(): String {
        if (isAnonymous || isSystem) {
            return who
        }

        return ToStringBuilder(this, SHORT_PREFIX_STYLE)
            .append("userId", authId)
            .append("roles", roles)
            .build()
    }
}