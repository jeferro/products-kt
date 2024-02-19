package com.jeferro.products.accounts.infrastructure.adapters.mongo.dtos

import com.jeferro.lib.infrastructure.shared.adapters.mongo.dtos.MetadataMongoDTO
import com.jeferro.lib.infrastructure.shared.adapters.mongo.dtos.MongoDocument
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "accounts")
class AccountMongoDTO(
    id: String,
    val username: String,
    val encodedPassword: String,
    val roles: List<String>,
    metadata: MetadataMongoDTO
) : MongoDocument(id, metadata)
