package com.jeferro.products.infrastructure.shared.components.rest.mappers

import com.jeferro.components.products.rest.dtos.AuthRestDTO
import com.jeferro.products.domain.shared.entities.auth.Auth
import com.jeferro.products.infrastructure.shared.utils.mappers.ToDTOMapper
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