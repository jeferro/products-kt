package com.jeferro.products.infrastructure.shared.components.rest.mappers

import com.jeferro.products.domain.shared.entities.auth.UserId
import com.jeferro.products.infrastructure.shared.utils.mappers.SimpleIdentifierMapper
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
abstract class UserIdRestMapper : SimpleIdentifierMapper<UserId, String>() {

    companion object {
        val instance: UserIdRestMapper = Mappers.getMapper(UserIdRestMapper::class.java)
    }

}