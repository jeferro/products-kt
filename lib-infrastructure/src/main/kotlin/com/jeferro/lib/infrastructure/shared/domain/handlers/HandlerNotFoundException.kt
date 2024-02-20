package com.jeferro.lib.infrastructure.shared.domain.handlers

import com.jeferro.lib.domain.exceptions.InternalException
import com.jeferro.lib.domain.handlers.Operation

class HandlerNotFoundException(
    message: String
) : InternalException(message, null) {

    companion object {
        fun create(operationClass: Class<Operation<*>>): HandlerNotFoundException {
            return HandlerNotFoundException("Handler not found by operation $operationClass")
        }
    }
}