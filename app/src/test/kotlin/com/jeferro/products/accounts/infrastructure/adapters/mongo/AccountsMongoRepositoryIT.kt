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
@Import(value = [AccountsMongoRepository::class])
class AccountsMongoRepositoryIT {

    companion object {

        @Container
        @ServiceConnection
        val mongodbContainer = MongodbContainerCreator.create()
    }

    private val accountMonoMapper = AccountsMongoMapper.instance

    @Autowired
    private lateinit var accountsMongoDao: AccountsMongoDao

    @Autowired
    private lateinit var accountsMongoRepository: AccountsMongoRepository

    @Nested
    inner class FindByUsername {

        @Test
        fun `should return data when account exists`() = runTest {
            val expected = oneAccount()
            initializeDatabaseWith(expected)

            val result = accountsMongoRepository.findByUsername(expected.username)

            assertEquals(expected, result)
        }

        @Test
        fun `should return null when account doesn't exist`() = runTest {
            initializeDatabaseWith()

            val username = oneUsername()
            val resultDTO = accountsMongoRepository.findByUsername(username)

            assertNull(resultDTO)
        }
    }

    private suspend fun initializeDatabaseWith(vararg products: Account) {
        accountsMongoDao.deleteAll()

        products.map { product -> accountMonoMapper.toDTO(product) }
            .forEach { productDTO -> accountsMongoDao.save(productDTO) }
    }
}