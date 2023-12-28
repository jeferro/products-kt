package com.jeferro.products.domain.authentications.entities

import com.jeferro.products.domain.shared.entities.auth.UserIdMother.oneUserId
import com.jeferro.products.domain.shared.entities.auth.UsernameMother.oneUsername
import com.jeferro.products.domain.shared.entities.metadata.MetadataMother.oneMetadata

object UserCredentialMother {

    const val ONE_USER_ENCRYPTED_PASSWORD = "encrypted-password"

    fun oneUserCredential(): UserCredentials {
        val roles = listOf("USER")
        val metadata = oneMetadata()

        return UserCredentials(
            oneUserId(),
            oneUsername(),
            ONE_USER_ENCRYPTED_PASSWORD,
            roles,
            metadata
        )
    }
}