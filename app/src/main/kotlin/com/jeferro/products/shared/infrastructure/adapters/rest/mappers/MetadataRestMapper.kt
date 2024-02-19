package com.jeferro.products.shared.infrastructure.adapters.rest.mappers

import com.jeferro.lib.domain.models.metadata.Metadata
import com.jeferro.lib.infrastructure.shared.adapters.mappers.ToDTOMapper
import com.jeferro.lib.infrastructure.shared.adapters.rest.mappers.InstantRestMapper
import com.jeferro.products.accounts.infrastructure.adapters.rest.mappers.UserIdRestMapper
import com.jeferro.products.shared.infrastructure.adapters.rest.dtos.MetadataRestDTO
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