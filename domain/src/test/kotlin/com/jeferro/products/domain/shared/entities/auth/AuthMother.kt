package com.jeferro.products.domain.shared.entities.auth

import com.jeferro.products.domain.Roles.ADMIN
import com.jeferro.products.domain.Roles.USER
import com.jeferro.products.domain.shared.entities.auth.Auth.Companion.authOfAnonymous
import com.jeferro.products.domain.shared.entities.auth.Auth.Companion.authOfSystem
import com.jeferro.products.domain.shared.entities.auth.Auth.Companion.authOfUser
import com.jeferro.products.domain.shared.entities.auth.UserIdMother.oneUserId

object AuthMother {

    fun oneAnonymousAuth(): Auth {
        return authOfAnonymous()
    }

    fun oneSystemAuth(): Auth {
        return authOfSystem()
    }

    fun oneUserAuth(): Auth {
        val userId = oneUserId()
        val roles = listOf(USER)

        return authOfUser(userId, roles)
    }

    fun oneUserAuth(userId: UserId): Auth {
        val roles = listOf(USER)

        return authOfUser(userId, roles)
    }

    fun oneUserAuth(roles: List<String>): Auth {
        val userId = oneUserId()

        return authOfUser(userId, roles)
    }

    fun oneAdminAuth(): Auth {
        val userId = oneUserId()
        val roles = listOf(ADMIN)

        return authOfUser(userId, roles)
    }
}