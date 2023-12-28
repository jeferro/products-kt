package com.jeferro.products.infrastructure.shared.components.mongo.mappers

import com.jeferro.products.components.products.mongo.shared.dtos.MetadataMongoDTOMother.oneMetadataMongoDTO
import com.jeferro.products.domain.shared.entities.metadata.MetadataMother.oneMetadata
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MetadataMongoMapperTest {

    private val metadataMongoMapper = MetadataMongoMapper.instance

    @Test
    fun `should map entity to dto and reverse successfully`() {
        val metadata = oneMetadata()

        val metadataDTO = metadataMongoMapper.toDTO(metadata)
        val result = metadataMongoMapper.toEntity(metadataDTO)

        assertEquals(metadata, result)
    }

    @Test
    fun `should map dto to entity and reverse successfully`() {
        val metadataDTO = oneMetadataMongoDTO()

        val metadata = metadataMongoMapper.toEntity(metadataDTO)
        val result = metadataMongoMapper.toDTO(metadata)

        assertEquals(metadataDTO, result)
    }
}