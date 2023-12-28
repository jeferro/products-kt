package com.jeferro.products.infrastructure.authentications.adapters.rest

import com.jeferro.components.products.rest.apis.AuthenticationsApi
import com.jeferro.components.products.rest.dtos.AuthRestDTO
import com.jeferro.components.products.rest.dtos.SignInInputRestDTO
import com.jeferro.products.domain.authentications.handlers.params.SignInParams
import com.jeferro.products.domain.shared.entities.auth.Auth.Companion.authOfAnonymous
import com.jeferro.products.domain.shared.handlers.bus.HandlerBus
import com.jeferro.products.infrastructure.shared.components.rest.mappers.AuthRestMapper
import com.jeferro.products.infrastructure.shared.components.rest.mappers.UsernameRestMapper
import com.jeferro.products.infrastructure.shared.security.jwt.JwtEncoder
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController


@RestController
class AuthenticationRestController(
    private val handlerBus: HandlerBus,
    private val jwtEncoder: JwtEncoder
) : AuthenticationsApi {

    companion object {
        val ANONYMOUS_AUTH = authOfAnonymous()
    }

    private val usernameRestMapper = UsernameRestMapper.instance

    private val authRestMapper = AuthRestMapper.instance

    override suspend fun authenticate(signInInputRestDTO: SignInInputRestDTO): ResponseEntity<AuthRestDTO> {
        val params = SignInParams(
            ANONYMOUS_AUTH,
            usernameRestMapper.toEntity(signInInputRestDTO.username),
            signInInputRestDTO.password
        )

        val auth = handlerBus.execute(params)

        val header = jwtEncoder.encode(auth)
        val authDTO = authRestMapper.toDTO(auth)

        return ResponseEntity.ok()
            .header("access-token", header)
            .body(authDTO)
    }
}