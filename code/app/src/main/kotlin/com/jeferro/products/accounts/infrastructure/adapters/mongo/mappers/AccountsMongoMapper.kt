package com.jeferro.products.accounts.infrastructure.adapters.mongo.mappers

import com.jeferro.lib.infrastructure.shared.adapters.mappers.BidireccionalMapper
import com.jeferro.products.accounts.domain.models.Account
import com.jeferro.products.accounts.infrastructure.adapters.mongo.dtos.AccountMongoDTO
import com.jeferro.products.shared.infrastructure.adapters.mongo.mappers.MetadataMongoMapper
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper(
    uses = [
        UserIdMongoMapper::class,
        UsernameMongoMapper::class,
        MetadataMongoMapper::class
    ]
)
abstract class AccountsMongoMapper : BidireccionalMapper<Account, AccountMongoDTO> {

    companion object {
        val instance: AccountsMongoMapper = Mappers.getMapper(AccountsMongoMapper::class.java)
    }
}