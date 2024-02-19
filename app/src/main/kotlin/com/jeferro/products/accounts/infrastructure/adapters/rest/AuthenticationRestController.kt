package com.jeferro.products.accounts.infrastructure.adapters.rest

import com.jeferro.lib.domain.handlers.HandlerBus
import com.jeferro.lib.domain.models.auth.Auth
import com.jeferro.lib.infrastructure.shared.security.AuthRestService
import com.jeferro.products.accounts.domain.handlers.params.SignInParams
import com.jeferro.products.accounts.infrastructure.adapters.rest.dtos.AuthRestDTO
import com.jeferro.products.accounts.infrastructure.adapters.rest.dtos.SignInInputRestDTO
import com.jeferro.products.accounts.infrastructure.adapters.rest.mappers.AuthRestMapper
import com.jeferro.products.accounts.infrastructure.adapters.rest.mappers.UsernameRestMapper
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
        val params = SignInParams(
            Auth.createOfAnonymous(),
            usernameRestMapper.toEntity(signInInputRestDTO.username),
            signInInputRestDTO.password
        )

        val auth = handlerBus.execute(params)

        val header = authRestService.getAuthenticationToken(auth)
        val authDTO = authRestMapper.toDTO(auth)

        return ResponseEntity.ok()
            .header("access-token", header)
            .body(authDTO)
    }
}