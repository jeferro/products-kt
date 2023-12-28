package com.jeferro.products.components.products.mongo.shared.dtos

import java.time.Instant

data class MetadataMongoDTO(
    val createdAt: Instant,
    val createdBy: String,
    val updatedAt: Instant,
    val updatedBy: String
)