package com.jeferro.lib.domain.handlers.bus

import com.jeferro.lib.domain.handlers.Operation

interface HandlerBus {
    suspend fun <R> execute(operation: Operation<R>): R
}
