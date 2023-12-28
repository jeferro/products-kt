package com.jeferro.products.infrastructure.shared.components.rest.mappers

import com.jeferro.products.domain.shared.entities.auth.UsernameMother.oneUsername
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UsernameRestMapperTest {

    private val usernameRestMapper = UsernameRestMapper.instance

    @Test
    fun `should map entity to dto and reverse successfully`() {
        val username = oneUsername()

        val usernameDTO = usernameRestMapper.toDTO(username)
        val result = usernameRestMapper.toEntity(usernameDTO)

        assertEquals(username, result)
    }

    @Test
    fun `should map dto to entity and reverse successfully`() {
        val usernameDTO = "one-username"

        val username = usernameRestMapper.toEntity(usernameDTO)
        val result = usernameRestMapper.toDTO(username)

        assertEquals(usernameDTO, result)
    }
}