package com.jeferro.products.accounts.domain.repositories

import com.jeferro.lib.domain.models.auth.UserId
import com.jeferro.products.accounts.domain.models.Account
import com.jeferro.products.accounts.domain.models.Username
import com.jeferro.products.shared.domain.repositories.FakeRepository

class FakeAccountRepository
    : AccountsRepository,
    FakeRepository<UserId, Account>() {

    override suspend fun findByUsername(username: Username): Account? {
        val values = data.filterValues { userCredentials -> userCredentials.username == username }
            .values

        if (values.isEmpty()) {
            return null
        }

        return values.first()
    }
}