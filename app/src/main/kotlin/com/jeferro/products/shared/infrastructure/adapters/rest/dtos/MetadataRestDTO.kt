package com.jeferro.products.shared.infrastructure.adapters.rest.dtos


data class MetadataRestDTO(

    val createdAt: java.time.OffsetDateTime,

    val createdBy: String,

    val updatedAt: java.time.OffsetDateTime,

    val updatedBy: String
) {

}

