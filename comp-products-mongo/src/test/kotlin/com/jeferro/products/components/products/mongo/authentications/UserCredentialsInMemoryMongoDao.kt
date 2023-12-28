package com.jeferro.products.components.products.mongo.authentications

import com.jeferro.products.components.products.mongo.authentications.dtos.UserCredentialsMongoDTO
import com.jeferro.products.components.products.mongo.shared.InMemoryMongoDao

class UserCredentialsInMemoryMongoDao : InMemoryMongoDao<UserCredentialsMongoDTO>(), UserCredentialsMongoDao {

    override suspend fun findByUsername(usernameDTO: String): UserCredentialsMongoDTO? {
        return data.values.find { userCredentials -> userCredentials.username == usernameDTO }
    }

}