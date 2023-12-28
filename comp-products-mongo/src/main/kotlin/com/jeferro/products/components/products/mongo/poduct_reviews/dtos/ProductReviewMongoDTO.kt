package com.jeferro.products.components.products.mongo.poduct_reviews.dtos

import com.jeferro.products.components.products.mongo.shared.dtos.MetadataMongoDTO
import com.jeferro.products.components.products.mongo.shared.dtos.MongoDocument
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "product-reviews")
class ProductReviewMongoDTO(
    id: String,
    val comment: String,
    metadata: MetadataMongoDTO
) : MongoDocument(id, metadata)
