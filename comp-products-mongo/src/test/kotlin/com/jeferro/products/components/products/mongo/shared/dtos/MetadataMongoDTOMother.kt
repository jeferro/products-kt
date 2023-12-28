package com.jeferro.products.components.products.mongo.shared.dtos

import java.time.Instant
import java.time.temporal.ChronoUnit

object MetadataMongoDTOMother {

    fun oneMetadataMongoDTO(): MetadataMongoDTO {
        val userId = "one-user-id"
        val now = Instant.now()
            .truncatedTo(ChronoUnit.MILLIS)

        return MetadataMongoDTO(
            now,
            userId,
            now,
            userId
        )
    }
}