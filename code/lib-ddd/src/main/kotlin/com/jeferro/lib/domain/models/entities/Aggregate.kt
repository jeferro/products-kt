package com.jeferro.lib.domain.models.entities

import com.jeferro.lib.domain.events.DomainEvent
import com.jeferro.lib.domain.models.auth.UserId
import com.jeferro.lib.domain.models.identifiers.Identifier
import com.jeferro.lib.domain.models.metadata.Metadata

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

    protected fun markAsModifyBy(authId: UserId) {
        metadata.markAsModifyBy(authId)
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