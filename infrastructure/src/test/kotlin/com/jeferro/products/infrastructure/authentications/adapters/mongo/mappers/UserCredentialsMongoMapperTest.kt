package com.jeferro.products.infrastructure.authentications.adapters.mongo.mappers

import com.jeferro.products.components.products.mongo.authentications.dtos.UserCredentialsMongoDTOMother.oneUserCredentialsMongoDTO
import com.jeferro.products.domain.authentications.entities.UserCredentialMother.oneUserCredential
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserCredentialsMongoMapperTest {

    private val userCredentialsMongoMapper = UserCredentialsMongoMapper.instance

    @Test
    fun `should map entity to dto and reverse successfully`() {
        val userCredentials = oneUserCredential()

        val userCredentialsDTO = userCredentialsMongoMapper.toDTO(userCredentials)
        val result = userCredentialsMongoMapper.toEntity(userCredentialsDTO)

        assertEquals(userCredentials, result)
    }

    @Test
    fun `should map dto to entity and reverse successfully`() {
        val userCredentialsDTO = oneUserCredentialsMongoDTO()

        val userCredentials = userCredentialsMongoMapper.toEntity(userCredentialsDTO)
        val result = userCredentialsMongoMapper.toDTO(userCredentials)

        assertEquals(userCredentialsDTO, result)
    }
}