package com.jeferro.products.components.products.mongo.authentications.dtos

import com.jeferro.products.components.products.mongo.shared.dtos.MetadataMongoDTOMother.oneMetadataMongoDTO

object UserCredentialsMongoDTOMother {

    fun oneUserCredentialsMongoDTO(): UserCredentialsMongoDTO {
        val username = "one-user-id"
        val metadataDTO = oneMetadataMongoDTO()

        return UserCredentialsMongoDTO(
            username,
            username,
            "encrypted-password",
            listOf("USER"),
            metadataDTO
        )
    }
}