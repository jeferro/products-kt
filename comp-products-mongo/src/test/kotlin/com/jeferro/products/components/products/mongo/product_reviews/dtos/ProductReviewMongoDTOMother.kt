package com.jeferro.products.components.products.mongo.product_reviews.dtos

import com.jeferro.products.components.products.mongo.poduct_reviews.dtos.ProductReviewMongoDTO
import com.jeferro.products.components.products.mongo.shared.dtos.MetadataMongoDTOMother.oneMetadataMongoDTO

object ProductReviewMongoDTOMother {

    fun oneProductReviewMongoDTO(): ProductReviewMongoDTO {
        val metadataDTO = oneMetadataMongoDTO()

        return ProductReviewMongoDTO(
            "one-product-id:one-user-id",
            "comment of product",
            metadataDTO
        )
    }
}