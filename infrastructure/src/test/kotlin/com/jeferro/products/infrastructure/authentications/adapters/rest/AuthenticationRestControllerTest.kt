package com.jeferro.products.infrastructure.authentications.adapters.rest

import com.jeferro.components.products.rest.dtos.AuthRestDTO
import com.jeferro.components.products.rest.dtos.SignInInputRestDTO
import com.jeferro.products.domain.authentications.handlers.params.SignInParams
import com.jeferro.products.domain.shared.entities.auth.Auth
import com.jeferro.products.domain.shared.entities.auth.Auth.Companion.authOfAnonymous
import com.jeferro.products.domain.shared.entities.auth.AuthMother.oneUserAuth
import com.jeferro.products.domain.shared.handlers.bus.HandlerBusInMemory
import com.jeferro.products.infrastructure.shared.components.rest.mappers.AuthRestMapper
import com.jeferro.products.infrastructure.shared.components.rest.mappers.UsernameRestMapper
import com.jeferro.products.infrastructure.shared.security.jwt.JwtEncoder
import com.jeferro.products.infrastructure.shared.security.jwt.JwtPropertiesMother.oneJwtProperties
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.ResponseEntity

@ExtendWith(MockitoExtension::class)
class AuthenticationRestControllerTest {

    private val usernameRestMapper = UsernameRestMapper.instance

    private val authRestMapper = AuthRestMapper.instance

    private val handlerBus = HandlerBusInMemory()

    private var jwtEncoder = JwtEncoder(
        oneJwtProperties()
    )

    private val authenticationRestController = AuthenticationRestController(
        handlerBus,
        jwtEncoder
    )

    @Nested
    inner class Authenticate {

        @Test
        fun `should authenticate through handler bus`() = runTest {
            val expectedAuth = oneUserAuth()
            handlerBus.reset(expectedAuth)

            val inputDTO = SignInInputRestDTO(
                "one-username",
                "password"
            )

            val response = authenticationRestController.authenticate(inputDTO)

            assertParams(
                inputDTO,
                handlerBus.first as SignInParams
            )

            assertResponse(
                expectedAuth,
                response
            )
        }

        private fun assertParams(
            inputDTO: SignInInputRestDTO,
            capturedParams: SignInParams
        ) {
            val expectedParams = SignInParams(
                authOfAnonymous(),
                usernameRestMapper.toEntity(inputDTO.username),
                inputDTO.password
            )

            assertEquals(expectedParams, capturedParams)
        }

        private fun assertResponse(
            expectedAuth: Auth,
            response: ResponseEntity<AuthRestDTO>
        ) {
            assertEquals(authRestMapper.toDTO(expectedAuth), response.body)
        }
    }

}