package com.jeferro.products.accounts.infrastructure.adapters.rest

import com.jeferro.lib.domain.exceptions.UnauthorizedException
import com.jeferro.lib.domain.handlers.HandlerBus
import com.jeferro.lib.domain.models.auth.Auth
import com.jeferro.products.accounts.domain.handlers.params.SignInParams
import com.jeferro.products.accounts.domain.models.Username
import com.jeferro.products.shared.domain.models.auth.AuthMother.oneUserAuth
import com.jeferro.products.shared.infrastructure.adapters.rest.RestITConfiguration
import com.jeferro.products.shared.infrastructure.adapters.rest.asyncDispatch
import com.jeferro.products.shared.infrastructure.adapters.rest.dtos.ErrorRestDTO
import kotlinx.coroutines.test.runTest
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

@WebMvcTest(AuthenticationRestController::class)
@Import(RestITConfiguration::class)
class AuthenticationRestControllerIT {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var handlerBus: HandlerBus

    @Nested
    inner class Authenticate {

        @Test
        fun `should authenticate user`() = runTest {
            val username = "one-user"
            val password = "one-password"
            val content = """{
            "username": "$username",
            "password": "$password"            
        }"""

            val expectedParams = SignInParams(
                Auth.createOfAnonymous(),
                Username(username),
                password
            )
            val expectedAuth = oneUserAuth()
            whenever(handlerBus.execute(expectedParams))
                .thenReturn(expectedAuth)

            val requestBuilder = MockMvcRequestBuilders.post("/v1/authentications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)

            mockMvc.perform(requestBuilder)
                .asyncDispatch(mockMvc)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value(expectedAuth.userId!!.value))
                .andExpect(jsonPath("$.roles").isArray())
                .andExpect(jsonPath("$.roles[0]").value(expectedAuth.roles[0]))
        }
    }
}