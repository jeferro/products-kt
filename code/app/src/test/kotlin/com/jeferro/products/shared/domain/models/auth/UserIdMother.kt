package com.jeferro.products.shared.domain.models.auth

import com.jeferro.lib.domain.models.auth.UserId

object UserIdMother {

    fun oneUserId(): UserId {
        return UserId("one-user")
    }

    fun twoUserId(): UserId {
        return UserId("two-user")
    }
}