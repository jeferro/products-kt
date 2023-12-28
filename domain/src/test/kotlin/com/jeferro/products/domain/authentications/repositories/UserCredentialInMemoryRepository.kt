package com.jeferro.products.domain.authentications.repositories

import com.jeferro.products.domain.authentications.entities.UserCredentials
import com.jeferro.products.domain.shared.entities.auth.UserId
import com.jeferro.products.domain.shared.entities.auth.Username
import com.jeferro.products.domain.shared.repositories.InMemoryRepository

class UserCredentialInMemoryRepository
    : UserCredentialsRepository,
    InMemoryRepository<UserId, UserCredentials>() {

    override suspend fun findByUsername(username: Username): UserCredentials? {
        val values = data.filterValues { userCredentials -> userCredentials.username == username }
            .values

        if (values.isEmpty()) {
            return null
        }

        return values.first()
    }
}