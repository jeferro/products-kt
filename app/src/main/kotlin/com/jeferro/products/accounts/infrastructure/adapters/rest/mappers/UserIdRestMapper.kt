package com.jeferro.products.accounts.infrastructure.adapters.rest.mappers

import com.jeferro.lib.domain.models.auth.UserId
import com.jeferro.lib.infrastructure.shared.adapters.mappers.SimpleIdentifierMapper
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
abstract class UserIdRestMapper : SimpleIdentifierMapper<UserId, String>() {

    companion object {
        val instance: UserIdRestMapper = Mappers.getMapper(UserIdRestMapper::class.java)
    }

}