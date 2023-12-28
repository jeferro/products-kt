package com.jeferro.products.components.products.mongo.products.dtos

import com.jeferro.products.components.products.mongo.shared.dtos.MetadataMongoDTOMother.oneMetadataMongoDTO

object ProductMongoDTOMother {

    fun oneProductMongoDTO(): ProductMongoDTO {
        val metadataDTO = oneMetadataMongoDTO()

        return ProductMongoDTO(
            "one-product-id",
            "title one product",
            "description one product",
            true,
            metadataDTO
        )
    }

    fun twoProductMongoDTO(): ProductMongoDTO {
        val metadataDTO = oneMetadataMongoDTO()

        return ProductMongoDTO(
            "two-product-id",
            "title two product",
            "description two product",
            true,
            metadataDTO
        )
    }
}