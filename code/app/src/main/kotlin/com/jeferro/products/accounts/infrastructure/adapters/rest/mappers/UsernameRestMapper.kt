package com.jeferro.products.accounts.infrastructure.adapters.rest.mappers

import com.jeferro.products.accounts.domain.models.Username
import com.jeferro.lib.infrastructure.shared.adapters.mappers.SimpleValueMapper
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
abstract class UsernameRestMapper : SimpleValueMapper<Username, String>() {

    companion object {
        val instance: UsernameRestMapper = Mappers.getMapper(UsernameRestMapper::class.java)
    }

}