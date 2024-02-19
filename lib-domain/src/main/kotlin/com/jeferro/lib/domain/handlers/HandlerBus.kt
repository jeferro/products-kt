package com.jeferro.lib.domain.handlers

interface HandlerBus {
    suspend fun <R> execute(params: HandlerParams<R>): R
}
