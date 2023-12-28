package com.jeferro.products.infrastructure.shared.components.mongo.mappers

import com.jeferro.products.domain.shared.entities.auth.UserIdMother.oneUserId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserIdMongoMapperTest {

    private val userIdMongoMapper = UserIdMongoMapper.instance

    @Test
    fun `should map entity to dto and reverse successfully`() {
        val userId = oneUserId()

        val userIdDTO = userIdMongoMapper.toDTO(userId)
        val result = userIdMongoMapper.toEntity(userIdDTO)

        assertEquals(userId, result)
    }

    @Test
    fun `should map dto to entity and reverse successfully`() {
        val userIdDTO = "one-user-id"

        val userId = userIdMongoMapper.toEntity(userIdDTO)
        val result = userIdMongoMapper.toDTO(userId)

        assertEquals(userIdDTO, result)
    }
}