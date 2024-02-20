package com.jeferro.products.shared.domain.events

import com.jeferro.lib.domain.events.DomainEvent
import com.jeferro.lib.domain.events.bus.EventBus

class EventBusInMemory : EventBus() {

    private val domainEvents = mutableListOf<DomainEvent>()

    val first
        get() = domainEvents.first()

    fun clear() {
        domainEvents.clear()
    }

    fun containsNumElements(num: Int): Boolean {
        return domainEvents.size == num
    }

    override suspend fun publishAll(events: List<DomainEvent>) {
        domainEvents.addAll(events)
    }
}