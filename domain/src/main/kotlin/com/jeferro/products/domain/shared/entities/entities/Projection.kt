package com.jeferro.products.domain.shared.entities.entities

import com.jeferro.products.domain.shared.entities.auth.UserId
import com.jeferro.products.domain.shared.entities.metadata.Metadata
import com.jeferro.products.domain.shared.entities.identifiers.Identifier

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