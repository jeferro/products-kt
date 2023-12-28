package com.jeferro.products.infrastructure.shared.components.mongo.mappers

import com.jeferro.products.domain.shared.entities.auth.UserId
import com.jeferro.products.infrastructure.shared.utils.mappers.SimpleIdentifierMapper
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
abstract class UserIdMongoMapper : SimpleIdentifierMapper<UserId, String>() {

    companion object {
        val instance: UserIdMongoMapper = Mappers.getMapper(UserIdMongoMapper::class.java)
    }
}
