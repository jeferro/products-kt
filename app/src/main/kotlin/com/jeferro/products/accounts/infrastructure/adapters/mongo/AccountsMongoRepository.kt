package com.jeferro.products.accounts.infrastructure.adapters.mongo

import com.jeferro.products.accounts.domain.models.Account
import com.jeferro.products.accounts.domain.models.Username
import com.jeferro.products.accounts.domain.repositories.AccountsRepository
import com.jeferro.products.accounts.infrastructure.adapters.mongo.daos.AccountsMongoDao
import com.jeferro.products.accounts.infrastructure.adapters.mongo.mappers.AccountsMongoMapper
import com.jeferro.products.accounts.infrastructure.adapters.mongo.mappers.UsernameMongoMapper
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Component
class AccountsMongoRepository(
    private val accountsMongoDao: AccountsMongoDao
) : AccountsRepository {

    private val usernameMongoMapper = UsernameMongoMapper.instance

    private val accountsMongoMapper = AccountsMongoMapper.instance

    override suspend fun findByUsername(username: Username): Account? {
        val usernameDTO = usernameMongoMapper.toDTO(username)

        val accountDTO = accountsMongoDao.findByUsername(usernameDTO)
            ?: return null

        return accountsMongoMapper.toEntity(accountDTO)
    }
}