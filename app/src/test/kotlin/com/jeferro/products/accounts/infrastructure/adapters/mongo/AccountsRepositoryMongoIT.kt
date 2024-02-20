package com.jeferro.products.accounts.infrastructure.adapters.mongo

import com.jeferro.products.accounts.domain.models.Account
import com.jeferro.products.accounts.domain.models.AccountMother.oneAccount
import com.jeferro.products.accounts.domain.models.UsernameMother.oneUsername
import com.jeferro.products.accounts.infrastructure.adapters.mongo.daos.AccountsMongoDao
import com.jeferro.products.accounts.infrastructure.adapters.mongo.mappers.AccountsMongoMapper
import com.jeferro.products.shared.infrastructure.adapters.mongo.MongodbContainerCreator
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Import
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@DataMongoTest
@Import(value = [AccountsRepositoryMongo::class])
class AccountsRepositoryMongoIT {

    companion object {

        @Container
        @ServiceConnection
        val mongodbContainer = MongodbContainerCreator.create()
    }

    @Autowired
    private lateinit var accountsMongoDao: AccountsMongoDao

    @Autowired
    private lateinit var accountsRepositoryMongo: AccountsRepositoryMongo

    @Nested
    inner class FindByUsernameTests {

        @Test
        fun `should return data when account exists`() = runTest {
            val expected = oneAccount()
            initializeDatabaseWith(expected)

            val result = accountsRepositoryMongo.findByUsername(expected.username)

            assertEquals(expected, result)
        }

        @Test
        fun `should return null when account doesn't exist`() = runTest {
            initializeDatabaseWith()

            val username = oneUsername()
            val result = accountsRepositoryMongo.findByUsername(username)

            assertNull(result)
        }
    }

    private suspend fun initializeDatabaseWith(vararg accounts: Account) {
        accountsMongoDao.deleteAll()

        val dtos = accounts.toList()
        AccountsMongoMapper.instance.toDTOList(dtos)
            .forEach { accountDTO -> accountsMongoDao.save(accountDTO) }
    }
}