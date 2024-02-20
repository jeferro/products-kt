package com.jeferro.products.accounts.domain.models

import com.jeferro.products.shared.domain.models.auth.UserIdMother.oneUserId
import com.jeferro.products.accounts.domain.models.UsernameMother.oneUsername
import com.jeferro.products.shared.domain.models.metadata.MetadataMother.oneMetadata

object AccountMother {

    const val ONE_ACCOUNT_ENCRYPTED_PASSWORD = "encrypted-password"

    fun oneAccount(): Account {
        val roles = listOf("USER")
        val metadata = oneMetadata()

        return Account(
            oneUserId(),
            oneUsername(),
            ONE_ACCOUNT_ENCRYPTED_PASSWORD,
            roles,
            metadata
        )
    }
}