package com.jeferro.lib.domain.events

import com.jeferro.lib.domain.models.entities.Aggregate

abstract class EventBus {

    suspend fun publishAll(aggregate: Aggregate<*>) {
        val events = aggregate.pullEvents()

        publishAll(events)
    }

    abstract suspend fun publishAll(events: List<DomainEvent>)
}