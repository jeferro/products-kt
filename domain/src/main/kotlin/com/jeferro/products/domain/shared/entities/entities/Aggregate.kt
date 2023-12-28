package com.jeferro.products.domain.shared.entities.entities

import com.jeferro.products.domain.shared.entities.auth.UserId
import com.jeferro.products.domain.shared.entities.identifiers.Identifier
import com.jeferro.products.domain.shared.entities.metadata.Metadata
import com.jeferro.products.domain.shared.events.DomainEvent

abstract class Aggregate<I : Identifier>(
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

    private val domainEvents = ArrayList<DomainEvent>()

    protected fun markAsModifyBy(userId: UserId) {
        metadata.markAsModifyBy(userId)
    }

    protected fun recordEvent(event: DomainEvent) {
        domainEvents.add(event)
    }

    fun pullEvents(): List<DomainEvent> {
        val currentEvents = ArrayList(domainEvents)

        domainEvents.clear()

        return currentEvents
    }
}