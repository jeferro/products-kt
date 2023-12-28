package com.jeferro.products.infrastructure.authentications.adapters.mongo.mappers

import com.jeferro.products.components.products.mongo.authentications.dtos.UserCredentialsMongoDTO
import com.jeferro.products.domain.authentications.entities.UserCredentials
import com.jeferro.products.infrastructure.shared.components.mongo.mappers.MetadataMongoMapper
import com.jeferro.products.infrastructure.shared.components.mongo.mappers.UserIdMongoMapper
import com.jeferro.products.infrastructure.shared.components.mongo.mappers.UsernameMongoMapper
import com.jeferro.products.infrastructure.shared.utils.mappers.BidireccionalMapper
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper(
    uses = [
        UserIdMongoMapper::class,
        UsernameMongoMapper::class,
        MetadataMongoMapper::class
    ]
)
abstract class UserCredentialsMongoMapper : BidireccionalMapper<UserCredentials, UserCredentialsMongoDTO> {

    companion object {
        val instance: UserCredentialsMongoMapper = Mappers.getMapper(UserCredentialsMongoMapper::class.java)
    }
}