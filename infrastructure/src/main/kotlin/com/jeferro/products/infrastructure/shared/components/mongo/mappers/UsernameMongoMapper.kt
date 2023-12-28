package com.jeferro.products.infrastructure.shared.components.mongo.mappers

import com.jeferro.products.domain.shared.entities.auth.Username
import com.jeferro.products.infrastructure.shared.utils.mappers.SimpleValueMapper
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
abstract class UsernameMongoMapper : SimpleValueMapper<Username, String>() {

    companion object {
        val instance: UsernameMongoMapper = Mappers.getMapper(UsernameMongoMapper::class.java)
    }
}