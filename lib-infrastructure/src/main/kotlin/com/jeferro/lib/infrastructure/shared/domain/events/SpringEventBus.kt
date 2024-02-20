package com.jeferro.lib.infrastructure.shared.domain.events

import com.jeferro.lib.domain.events.BaseEventHandler
import com.jeferro.lib.domain.events.DomainEvent
import com.jeferro.lib.domain.events.EventBus
import com.jeferro.lib.domain.models.entities.Aggregate
import java.lang.reflect.ParameterizedType

class SpringEventBus : EventBus() {

    private val subscribersByEvent = mutableMapOf<Class<out DomainEvent>, MutableList<BaseEventHandler<*, *>>>()

    suspend fun publishOf(aggregate: Aggregate<*>) {
        val events = aggregate.pullEvents()

        publishAll(events)
    }

    override suspend fun publishAll(events: List<DomainEvent>) {
        // TODO
    }

    protected suspend fun onConsume(event: DomainEvent) {
        val subscribers = getSubscribers(event)

        subscribers.forEach { it.execute(event) }
    }

    @Suppress("UNCHECKED_CAST")
    protected fun registrySubscriber(subscriber: BaseEventHandler<*, *>) {
        val type = subscriber.javaClass.genericSuperclass

        if (type !is ParameterizedType) {
            throw IllegalArgumentException("Subscriber superclass is not a parameterized type")
        }

        val operationClass = type.actualTypeArguments[0] as Class<out DomainEvent>

        if (subscribersByEvent[operationClass] != null) {
            subscribersByEvent[operationClass]?.add(subscriber)
        } else {
            subscribersByEvent[operationClass] = mutableListOf(subscriber)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <E : DomainEvent> getSubscribers(event: E): MutableList<BaseEventHandler<E, *>> {
        val operationClass = event.javaClass

        val result = subscribersByEvent[operationClass] ?: mutableListOf()

        return result as MutableList<BaseEventHandler<E, *>>
    }
}