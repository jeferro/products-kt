package com.jeferro.components.products.rest.dtos

import java.time.OffsetDateTime

object MetadataRestDTOMother {

    fun oneMetadataRestDTO(): MetadataRestDTO {
        val now = OffsetDateTime.now()

        return MetadataRestDTO(
            now,
            "one-user-id",
            now,
            "one-user-id"
        )
    }
}