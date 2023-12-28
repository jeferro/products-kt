package com.jeferro.products.infrastructure.shared.components.rest.mappers

import com.jeferro.components.products.rest.dtos.MetadataRestDTO
import com.jeferro.products.domain.shared.entities.metadata.Metadata
import com.jeferro.products.infrastructure.shared.utils.mappers.ToDTOMapper
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper(
    uses = [
        UserIdRestMapper::class,
        InstantRestMapper::class
    ]
)
abstract class MetadataRestMapper : ToDTOMapper<Metadata, MetadataRestDTO> {

    companion object {
        val instance = Mappers.getMapper(MetadataRestMapper::class.java)
    }
}