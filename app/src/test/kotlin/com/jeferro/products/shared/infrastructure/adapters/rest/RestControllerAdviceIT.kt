package com.jeferro.products.shared.infrastructure.adapters.rest

import com.jeferro.lib.domain.exceptions.*
import com.jeferro.products.shared.infrastructure.adapters.rest.dtos.ErrorRestDTO
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(value = [RestControllerAdvice::class, FakeRestController::class])
@Import(RestITConfiguration::class)
class RestControllerAdviceIT {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var fakeRestController: FakeRestController


    @Nested
    inner class ValueValidationExceptions {

        @Test
        fun `should map value validation exceptions`() {
            whenever(fakeRestController.fake())
                .thenThrow(ValueValidationException::class.java)

            val requestBuilder = MockMvcRequestBuilders.get("/fake")
                .contentType(MediaType.APPLICATION_JSON)

            mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ErrorRestDTO.Code.VALUE_VALIDATION.value))
        }

        @Test
        fun `should map illegal argument exceptions`() {
            whenever(fakeRestController.fake())
                .thenThrow(IllegalArgumentException::class.java)

            val requestBuilder = MockMvcRequestBuilders.get("/fake")
                .contentType(MediaType.APPLICATION_JSON)

            mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ErrorRestDTO.Code.VALUE_VALIDATION.value))
        }
    }

    @Test
    fun `should map not found exceptions`() {
        whenever(fakeRestController.fake())
            .thenThrow(NotFoundException::class.java)

        val requestBuilder = MockMvcRequestBuilders.get("/fake")
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(requestBuilder)
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.code").value(ErrorRestDTO.Code.NOT_FOUND.value))
    }

    @Test
    fun `should map unauthorized exceptions`() {
        whenever(fakeRestController.fake())
            .thenThrow(UnauthorizedException::class.java)

        val requestBuilder = MockMvcRequestBuilders.get("/fake")
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(requestBuilder)
            .andExpect(status().isUnauthorized())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.code").value(ErrorRestDTO.Code.UNAUTHORIZED.value))
    }

    @Test
    fun `should map forbidden exceptions`() {
        whenever(fakeRestController.fake())
            .thenThrow(ForbiddenException::class.java)

        val requestBuilder = MockMvcRequestBuilders.get("/fake")
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(requestBuilder)
            .andExpect(status().isForbidden())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.code").value(ErrorRestDTO.Code.FORBIDDEN.value))
    }

    @Test
    fun `should map internal exceptions`() {
        whenever(fakeRestController.fake())
            .thenThrow(InternalException::class.java)

        val requestBuilder = MockMvcRequestBuilders.get("/fake")
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(requestBuilder)
            .andExpect(status().isInternalServerError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.code").value(ErrorRestDTO.Code.INTERNAL_ERROR.value))
    }

    @Test
    fun `should map other exceptions`() {
        whenever(fakeRestController.fake())
            .thenThrow(NullPointerException::class.java)

        val requestBuilder = MockMvcRequestBuilders.get("/fake")
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(requestBuilder)
            .andExpect(status().isInternalServerError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.code").value(ErrorRestDTO.Code.INTERNAL_ERROR.value))
    }

    @Test
    fun `should map exception with message`() {
        val expectedMessage = "NPE in code"
        whenever(fakeRestController.fake())
            .thenThrow(NullPointerException(expectedMessage))

        val requestBuilder = MockMvcRequestBuilders.get("/fake")
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(requestBuilder)
            .andExpect(status().isInternalServerError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.code").value(ErrorRestDTO.Code.INTERNAL_ERROR.value))
            .andExpect(jsonPath("$.message").value(expectedMessage))
    }


}