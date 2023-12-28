package com.jeferro.products.domain.shared.handlers.exceptions

import com.jeferro.products.domain.shared.exceptions.InternalException
import com.jeferro.products.domain.shared.handlers.params.Params

class HandlerNotFoundException(
    message: String
) : InternalException(message, null) {

    companion object {
        fun handlerNotFoundExceptionOf(paramsClass: Class<Params<*>>): HandlerNotFoundException {
            return HandlerNotFoundException("Handler by $paramsClass not found")
        }
    }
}