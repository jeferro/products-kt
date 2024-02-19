package com.jeferro.lib.domain.models.entities

import com.jeferro.lib.domain.models.auth.UserId
import com.jeferro.lib.domain.models.identifiers.Identifier
import com.jeferro.lib.domain.models.metadata.Metadata

abstract class Projection<I : Identifier>(
    id: I,
    val metadata: Metadata
) : Entity<I>(id) {

    val createdAt
        get() = this.metadata.createdAt

    val createdBy
        get() = this.metadata.createdBy

    val updatedAt
        get() = this.metadata.updatedAt

    val updatedBy
        get() = this.metadata.updatedBy

    protected fun markAsModifyBy(userId: UserId) {
        metadata.markAsModifyBy(userId)
    }
}