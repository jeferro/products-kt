package com.jeferro.products.accounts.infrastructure.adapters.mongo.mappers

import com.jeferro.products.accounts.domain.models.Username
import com.jeferro.lib.infrastructure.shared.adapters.mappers.SimpleValueMapper
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
abstract class UsernameMongoMapper : SimpleValueMapper<Username, String>() {

    companion object {
        val instance: UsernameMongoMapper = Mappers.getMapper(UsernameMongoMapper::class.java)
    }
}