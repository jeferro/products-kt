package com.jeferro.products.products.infrastructure.adapters.mongo.dtos

import com.jeferro.lib.infrastructure.shared.adapters.mongo.dtos.MetadataMongoDTO
import com.jeferro.lib.infrastructure.shared.adapters.mongo.dtos.MongoDocument
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "products")
class ProductMongoDTO(
    id: String,
    val title: String,
    val description: String,
    val enabled: Boolean,
    metadata: MetadataMongoDTO
) : MongoDocument(id, metadata)
