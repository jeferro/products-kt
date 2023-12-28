package com.jeferro.products.infrastructure.authentications.adapters.mongo

import com.jeferro.products.components.products.mongo.authentications.UserCredentialsInMemoryMongoDao
import com.jeferro.products.components.products.mongo.authentications.dtos.UserCredentialsMongoDTOMother.oneUserCredentialsMongoDTO
import com.jeferro.products.domain.shared.entities.auth.UsernameMother.oneUsername
import com.jeferro.products.infrastructure.authentications.adapters.mongo.mappers.UserCredentialsMongoMapper
import com.jeferro.products.infrastructure.shared.components.mongo.mappers.UsernameMongoMapper
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class UserCredentialsMongoRepositoryTest {

    private val usernameMongoMapper = UsernameMongoMapper.instance

    private val userCredentialsMongoMapper = UserCredentialsMongoMapper.instance

    private val userCredentialsInMemoryMongoDao = UserCredentialsInMemoryMongoDao()

    private val userCredentialsMongoRepository = UserCredentialsMongoRepository(
        userCredentialsInMemoryMongoDao
    )

    @Nested
    inner class FindByUsername {

        @Test
        fun `should return user when he exists`() = runTest {
            val userCredentialsDTO = oneUserCredentialsMongoDTO()
            userCredentialsInMemoryMongoDao.reset(userCredentialsDTO)

            val username = usernameMongoMapper.toEntity(userCredentialsDTO.username)
            val result = userCredentialsMongoRepository.findByUsername(username)

            assertEquals(userCredentialsMongoMapper.toEntity(userCredentialsDTO), result)
        }

        @Test
        fun `should return null when user doesn't exist`() = runTest {
            userCredentialsInMemoryMongoDao.reset()

            val username = oneUsername()
            val result = userCredentialsMongoRepository.findByUsername(username)

            assertNull(result)
        }
    }
}