package com.jeferro.products.components.products.mongo.authentications.dtos

import com.jeferro.products.components.products.mongo.shared.dtos.MetadataMongoDTO
import com.jeferro.products.components.products.mongo.shared.dtos.MongoDocument
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user-credentials")
class UserCredentialsMongoDTO(
    id: String,
    val username: String,
    val encodedPassword: String,
    val roles: List<String>,
    metadata: MetadataMongoDTO
) : MongoDocument(id, metadata)
