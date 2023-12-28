package com.jeferro.products.domain.shared.handlers.bus

import com.jeferro.products.domain.shared.handlers.params.Params

class HandlerBusInMemory: HandlerBus() {

    private val handlerParams = mutableListOf<Params<*>>()

    private lateinit var result: Any

    val first
        get() = handlerParams.first()

    fun reset(result: Any) {
        handlerParams.clear()

        this.result = result
    }

    fun containsNumElements(num: Int): Boolean {
        return handlerParams.size == num
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun <R> execute(params: Params<R>): R {
        handlerParams.add(params)

        return result as R
    }

}