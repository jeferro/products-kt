package com.jeferro.lib.domain.events

interface EventBus {

    suspend fun publishAll(events: List<DomainEvent>)
}