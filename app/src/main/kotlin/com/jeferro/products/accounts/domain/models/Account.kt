package com.jeferro.products.accounts.domain.models

import com.jeferro.lib.domain.models.auth.UserId
import com.jeferro.lib.domain.models.entities.Aggregate
import com.jeferro.lib.domain.models.metadata.Metadata

class Account(
    id: UserId,
    val username: Username,
    val encodedPassword: String,
    val roles: List<String>,
    metadata: Metadata
) : Aggregate<UserId>(id, metadata)
