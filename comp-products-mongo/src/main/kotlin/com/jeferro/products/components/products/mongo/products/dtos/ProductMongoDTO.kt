package com.jeferro.products.components.products.mongo.products.dtos

import com.jeferro.products.components.products.mongo.shared.dtos.MetadataMongoDTO
import com.jeferro.products.components.products.mongo.shared.dtos.MongoDocument
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "products")
class ProductMongoDTO(
    id: String,
    val title: String,
    val description: String,
    val enabled: Boolean,
    metadata: MetadataMongoDTO
) : MongoDocument(id, metadata)
