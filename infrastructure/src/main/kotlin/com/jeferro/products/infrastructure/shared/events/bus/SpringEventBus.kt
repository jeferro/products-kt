package com.jeferro.products.infrastructure.shared.events.bus

import com.jeferro.products.domain.shared.events.DomainEvent
import com.jeferro.products.domain.shared.events.bus.EventBus
import com.jeferro.products.domain.shared.events.handlers.BaseEventHandler
import kotlinx.coroutines.coroutineScope
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class SpringEventBus(
    applicationContext: ApplicationContext
) : EventBus() {

    init {
        applicationContext.getBeansOfType(BaseEventHandler::class.java)
            .values
            .forEach { subscriber -> registrySubscriber(subscriber) }
    }

    override suspend fun publishAll(events: List<DomainEvent>) {
        coroutineScope {
            events.forEach { onConsume(it) }
        }
    }
}