package com.jeferro.products.components.products.mongo.authentications

import com.jeferro.products.components.products.mongo.authentications.dtos.UserCredentialsMongoDTO
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserCredentialsMongoDao : CoroutineCrudRepository<UserCredentialsMongoDTO, String> {

    suspend fun findByUsername(usernameDTO: String): UserCredentialsMongoDTO?
}