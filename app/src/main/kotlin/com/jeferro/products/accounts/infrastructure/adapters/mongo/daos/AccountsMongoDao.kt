package com.jeferro.products.accounts.infrastructure.adapters.mongo.daos

import com.jeferro.products.accounts.infrastructure.adapters.mongo.dtos.AccountMongoDTO
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountsMongoDao : CoroutineCrudRepository<AccountMongoDTO, String> {

    suspend fun findByUsername(usernameDTO: String): AccountMongoDTO?
}