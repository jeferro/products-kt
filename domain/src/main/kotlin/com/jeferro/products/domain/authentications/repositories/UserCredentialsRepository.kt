package com.jeferro.products.domain.authentications.repositories

import com.jeferro.products.domain.authentications.entities.UserCredentials
import com.jeferro.products.domain.shared.entities.auth.Username

interface UserCredentialsRepository {

    suspend fun findByUsername(username: Username): UserCredentials?
}