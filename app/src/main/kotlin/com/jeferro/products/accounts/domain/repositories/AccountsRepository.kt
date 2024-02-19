package com.jeferro.products.accounts.domain.repositories

import com.jeferro.products.accounts.domain.models.Username
import com.jeferro.products.accounts.domain.models.Account

interface AccountsRepository {

    suspend fun findByUsername(username: Username): Account?
}