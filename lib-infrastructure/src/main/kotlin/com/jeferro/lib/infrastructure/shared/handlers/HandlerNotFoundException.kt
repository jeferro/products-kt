package com.jeferro.lib.infrastructure.shared.handlers

import com.jeferro.lib.domain.exceptions.InternalException
import com.jeferro.lib.domain.handlers.HandlerParams

class HandlerNotFoundException(
    message: String
) : InternalException(message, null) {

    companion object {
        fun create(handlerParamsClass: Class<HandlerParams<*>>): HandlerNotFoundException {
            return HandlerNotFoundException("Handler not found by params $handlerParamsClass")
        }
    }
}