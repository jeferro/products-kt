package com.jeferro.products.components.products.mongo.authentications

import com.jeferro.products.components.products.mongo.MongodbContainerCreator
import com.jeferro.products.components.products.mongo.authentications.dtos.UserCredentialsMongoDTOMother.oneUserCredentialsMongoDTO
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers


@AutoConfigureDataMongo
@SpringBootTest
@Testcontainers
class UserCredentialsDaoIT {

    companion object {

        @Container
        @ServiceConnection
        val mongodbContainer = MongodbContainerCreator.create()
    }

    @Autowired
    private lateinit var userCredentialsMongoDao: UserCredentialsMongoDao

    @BeforeEach
    fun beforeEach() = runTest {
        userCredentialsMongoDao.deleteAll()
    }

    @Nested
    inner class FindByUsername {

        @Test
        fun `should return user when he exists`() = runTest {
            val userCredentialsDTO = oneUserCredentialsMongoDTO()
            userCredentialsMongoDao.save(userCredentialsDTO)

            val resultDTO = userCredentialsMongoDao.findByUsername(userCredentialsDTO.username)

            assertEquals(userCredentialsDTO, resultDTO)
        }

        @Test
        fun `should return null when he doesn't exist`() = runTest {
            val resultDTO = userCredentialsMongoDao.findByUsername("unknown")

            assertNull(resultDTO)
        }
    }
}
