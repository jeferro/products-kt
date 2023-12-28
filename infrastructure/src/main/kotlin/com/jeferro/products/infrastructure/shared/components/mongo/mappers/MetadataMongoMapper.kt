package com.jeferro.products.infrastructure.shared.components.mongo.mappers

import com.jeferro.products.components.products.mongo.shared.dtos.MetadataMongoDTO
import com.jeferro.products.domain.shared.entities.metadata.Metadata
import com.jeferro.products.infrastructure.shared.utils.mappers.BidireccionalMapper
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