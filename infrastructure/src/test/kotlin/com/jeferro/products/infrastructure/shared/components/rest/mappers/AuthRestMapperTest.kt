package com.jeferro.products.infrastructure.shared.components.rest.mappers

import com.jeferro.products.domain.shared.entities.auth.AuthMother.oneUserAuth
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AuthRestMapperTest {

    private val authRestMapper = AuthRestMapper.instance

    @Test
    fun `should map to dto`() {
        val auth = oneUserAuth()

        val result = authRestMapper.toDTO(auth)

        assertEquals(auth.userId?.value, result.userId)
        assertEquals(auth.roles, result.roles)
    }
}