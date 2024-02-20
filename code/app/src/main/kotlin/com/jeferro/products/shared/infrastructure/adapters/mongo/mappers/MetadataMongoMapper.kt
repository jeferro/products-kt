package com.jeferro.products.shared.infrastructure.adapters.mongo.mappers

import com.jeferro.lib.domain.models.metadata.Metadata
import com.jeferro.lib.infrastructure.shared.adapters.mappers.BidireccionalMapper
import com.jeferro.lib.infrastructure.shared.adapters.mongo.dtos.MetadataMongoDTO
import com.jeferro.products.accounts.infrastructure.adapters.mongo.mappers.UserIdMongoMapper
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper(
    uses = [
        UserIdMongoMapper::class
    ]
)
abstract class MetadataMongoMapper : BidireccionalMapper<Metadata, MetadataMongoDTO> {

    companion object {
        val instance: MetadataMongoMapper = Mappers.getMapper(MetadataMongoMapper::class.java)
    }

    abstract override fun toEntity(dto: MetadataMongoDTO): Metadata
}