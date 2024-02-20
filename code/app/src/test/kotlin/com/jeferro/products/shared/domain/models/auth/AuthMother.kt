package com.jeferro.products.shared.domain.models.auth

import com.jeferro.lib.domain.models.auth.Auth
import com.jeferro.lib.domain.models.auth.UserId
import com.jeferro.products.shared.domain.models.auth.UserIdMother.oneUserId
import com.jeferro.products.shared.Roles

object AuthMother {

    fun oneAnonymousAuth(): Auth {
        return Auth.createOfAnonymous()
    }

    fun oneSystemAuth(): Auth {
        return Auth.createOfSystem()
    }

    fun oneUserAuth(): Auth {
        val userId = oneUserId()
        val roles = listOf(Roles.USER)

        return Auth.createOfUser(userId, roles)
    }

    fun oneUserAuth(userId: UserId): Auth {
        val roles = listOf(Roles.USER)

        return Auth.createOfUser(userId, roles)
    }

    fun oneUserAuth(roles: List<String>): Auth {
        val userId = oneUserId()

        return Auth.createOfUser(userId, roles)
    }

    fun oneAdminAuth(): Auth {
        val userId = oneUserId()
        val roles = listOf(Roles.ADMIN)

        return Auth.createOfUser(userId, roles)
    }
}