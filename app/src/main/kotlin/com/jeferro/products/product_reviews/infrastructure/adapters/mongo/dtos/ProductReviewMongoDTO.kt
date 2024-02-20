package com.jeferro.products.product_reviews.infrastructure.adapters.mongo.dtos

import com.jeferro.lib.infrastructure.shared.adapters.mongo.dtos.MetadataMongoDTO
import com.jeferro.lib.infrastructure.shared.adapters.mongo.dtos.MongoDocument
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "product-reviews")
class ProductReviewMongoDTO(
    id: String,
    val comment: String,
    metadata: MetadataMongoDTO
) : MongoDocument(id, metadata)
