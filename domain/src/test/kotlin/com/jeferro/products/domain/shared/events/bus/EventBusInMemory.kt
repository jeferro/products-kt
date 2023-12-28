package com.jeferro.products.domain.shared.events.bus

import com.jeferro.products.domain.shared.events.DomainEvent

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