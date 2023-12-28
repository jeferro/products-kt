package com.jeferro.products.domain.shared.events.bus

import com.jeferro.products.domain.shared.entities.entities.Aggregate
import com.jeferro.products.domain.shared.events.DomainEvent
import com.jeferro.products.domain.shared.events.handlers.BaseEventHandler
import java.lang.reflect.ParameterizedType

abstract class EventBus {

    private val subscribersByEvent = mutableMapOf<Class<out DomainEvent>, MutableList<BaseEventHandler<*, *>>>()

    suspend fun publishOf(aggregate: Aggregate<*>) {
        val events = aggregate.pullEvents()

        publishAll(events)
    }

    protected abstract suspend fun publishAll(events: List<DomainEvent>)

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

        val paramsClass = type.actualTypeArguments[0] as Class<out DomainEvent>

        if (subscribersByEvent[paramsClass] != null) {
            subscribersByEvent[paramsClass]?.add(subscriber)
        } else {
            subscribersByEvent[paramsClass] = mutableListOf(subscriber)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <E : DomainEvent> getSubscribers(event: E): MutableList<BaseEventHandler<E, *>> {
        val paramsClass = event.javaClass

        val result = subscribersByEvent[paramsClass] ?: mutableListOf()

        return result as MutableList<BaseEventHandler<E, *>>
    }
}