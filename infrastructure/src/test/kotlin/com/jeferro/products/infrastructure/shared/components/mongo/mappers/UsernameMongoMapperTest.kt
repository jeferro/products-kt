package com.jeferro.products.infrastructure.shared.components.mongo.mappers

import com.jeferro.products.domain.shared.entities.auth.UsernameMother.oneUsername
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class UsernameMongoMapperTest {

    private val usernameMongoMapper = UsernameMongoMapper.instance

    @Test
    fun `should map entity to dto and reverse successfully`() {
        val username = oneUsername()

        val usernameDTO = usernameMongoMapper.toDTO(username)
        val result = usernameMongoMapper.toEntity(usernameDTO)

        assertEquals(username, result)
    }

    @Test
    fun `should map dto to entity and reverse successfully`() {
        val usernameDTO = "one-username"

        val username = usernameMongoMapper.toEntity(usernameDTO)
        val result = usernameMongoMapper.toDTO(username)

        assertEquals(usernameDTO, result)
    }
}