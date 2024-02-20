package com.jeferro.products.accounts.infrastructure.adapters.rest

import com.jeferro.lib.application.bus.HandlerBus
import com.jeferro.lib.domain.models.auth.Auth
import com.jeferro.lib.infrastructure.shared.security.services.AuthRestService
import com.jeferro.products.accounts.application.operations.SignIn
import com.jeferro.products.accounts.infrastructure.adapters.rest.mappers.AuthRestMapper
import com.jeferro.products.accounts.infrastructure.adapters.rest.mappers.UsernameRestMapper
import com.jeferro.products.components.products.rest.dtos.AuthRestDTO
import com.jeferro.products.components.products.rest.dtos.SignInInputRestDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController


@RestController
class AuthenticationRestController(
    private val handlerBus: HandlerBus,
    private val authRestService: AuthRestService
) {

    private val usernameRestMapper = UsernameRestMapper.instance

    private val authRestMapper = AuthRestMapper.instance

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/v1/authentications"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    suspend fun authenticate(@RequestBody signInInputRestDTO: SignInInputRestDTO): ResponseEntity<AuthRestDTO> {
        val operation = SignIn(
            Auth.createOfAnonymous(),
            usernameRestMapper.toEntity(signInInputRestDTO.username),
            signInInputRestDTO.password
        )

        val auth = handlerBus.execute(operation)

        val header = authRestService.getAuthenticationToken(auth)
        val authDTO = authRestMapper.toDTO(auth)

        return ResponseEntity.ok()
            .header("access-token", header)
            .body(authDTO)
    }
}