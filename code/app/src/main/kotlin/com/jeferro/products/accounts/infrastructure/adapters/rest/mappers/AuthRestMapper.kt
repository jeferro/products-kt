package com.jeferro.products.accounts.infrastructure.adapters.rest.mappers

import com.jeferro.lib.domain.models.auth.Auth
import com.jeferro.lib.infrastructure.shared.adapters.mappers.ToDTOMapper
import com.jeferro.products.components.products.rest.dtos.AuthRestDTO
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper(
    uses = [
        UserIdRestMapper::class
    ]
)
abstract class AuthRestMapper : ToDTOMapper<Auth, AuthRestDTO> {

    companion object {
        val instance: AuthRestMapper = Mappers.getMapper(AuthRestMapper::class.java)
    }

    abstract override fun toDTO(entity: Auth): AuthRestDTO
}