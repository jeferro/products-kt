package com.jeferro.products.accounts.infrastructure.adapters.mongo.mappers

import com.jeferro.lib.domain.models.auth.UserId
import com.jeferro.lib.infrastructure.shared.adapters.mappers.SimpleIdentifierMapper
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
abstract class UserIdMongoMapper : SimpleIdentifierMapper<UserId, String>() {

    companion object {
        val instance: UserIdMongoMapper = Mappers.getMapper(UserIdMongoMapper::class.java)
    }
}
