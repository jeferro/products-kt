package com.jeferro.products.infrastructure.authentications.adapters.mongo

import com.jeferro.products.components.products.mongo.authentications.UserCredentialsMongoDao
import com.jeferro.products.domain.authentications.entities.UserCredentials
import com.jeferro.products.domain.authentications.repositories.UserCredentialsRepository
import com.jeferro.products.domain.shared.entities.auth.Username
import com.jeferro.products.infrastructure.authentications.adapters.mongo.mappers.UserCredentialsMongoMapper
import com.jeferro.products.infrastructure.shared.components.mongo.mappers.UsernameMongoMapper
import org.springframework.stereotype.Repository

@Repository
class UserCredentialsMongoRepository(
    private val userCredentialsMongoDao: UserCredentialsMongoDao
) : UserCredentialsRepository {

    private val usernameMongoMapper = UsernameMongoMapper.instance

    private val userCredentialsMongoMapper = UserCredentialsMongoMapper.instance

    override suspend fun findByUsername(username: Username): UserCredentials? {
        val usernameDTO = usernameMongoMapper.toDTO(username)

        val userCredentialsDTO = userCredentialsMongoDao.findByUsername(usernameDTO)
            ?: return null

        return userCredentialsMongoMapper.toEntity(userCredentialsDTO)
    }
}