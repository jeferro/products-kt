package com.jeferro.products.domain.shared.handlers.bus

import com.jeferro.products.domain.shared.handlers.exceptions.HandlerNotFoundException.Companion.handlerNotFoundExceptionOf
import com.jeferro.products.domain.shared.handlers.BaseHandler
import com.jeferro.products.domain.shared.handlers.params.Params
import java.lang.IllegalArgumentException
import java.lang.reflect.ParameterizedType

abstract class HandlerBus {

    private val handlers = mutableMapOf<Class<out Params<*>>, BaseHandler<*, *>?>()

    open suspend fun <R> execute(params: Params<R>): R {
        val handler = getHandler(params)

        return handler.execute(params)
    }

    @Suppress("UNCHECKED_CAST")
    protected fun registryHandler(handler: BaseHandler<*, *>) {
        val type = handler.javaClass.genericSuperclass

        if( type !is ParameterizedType){
            throw IllegalArgumentException("Handler superclass is not a parameterized type")
        }

        val paramsClass = type.actualTypeArguments[0] as Class<out Params<*>>

        handlers[paramsClass] = handler
    }

    @Suppress("UNCHECKED_CAST")
    private fun <R> getHandler(params: Params<R>): BaseHandler<Params<R>, R> {
        val paramsClass = params.javaClass

        val handler = handlers[paramsClass]
            ?: throw handlerNotFoundExceptionOf(paramsClass as Class<Params<*>>)

        return handler as BaseHandler<Params<R>, R>
    }
}