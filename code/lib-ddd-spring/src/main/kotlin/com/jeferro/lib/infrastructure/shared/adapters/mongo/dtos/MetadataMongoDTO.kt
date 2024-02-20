package com.jeferro.lib.infrastructure.shared.adapters.mongo.dtos

import java.time.Instant

data class MetadataMongoDTO(
    val createdAt: Instant,
    val createdBy: String,
    val updatedAt: Instant,
    val updatedBy: String
)