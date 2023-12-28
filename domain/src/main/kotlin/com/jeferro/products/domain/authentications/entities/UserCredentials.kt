package com.jeferro.products.domain.authentications.entities

import com.jeferro.products.domain.shared.entities.entities.Aggregate
import com.jeferro.products.domain.shared.entities.metadata.Metadata
import com.jeferro.products.domain.shared.entities.auth.UserId
import com.jeferro.products.domain.shared.entities.auth.Username

class UserCredentials(
    id: UserId,
    val username: Username,
    val encodedPassword: String,
    val roles: List<String>,
    metadata: Metadata
) : Aggregate<UserId>(id, metadata)
