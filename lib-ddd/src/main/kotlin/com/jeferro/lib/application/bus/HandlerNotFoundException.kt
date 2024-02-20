package com.jeferro.lib.application.bus

import com.jeferro.lib.domain.exceptions.InternalException
import com.jeferro.lib.application.operations.Operation

class HandlerNotFoundException(
    message: String
) : InternalException(message, null) {

    companion object {
        fun create(operationClass: Class<Operation<*>>): HandlerNotFoundException {
            return HandlerNotFoundException("Handler not found by operation $operationClass")
        }
    }
}