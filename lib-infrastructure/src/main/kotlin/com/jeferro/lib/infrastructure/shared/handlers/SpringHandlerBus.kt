package com.jeferro.lib.infrastructure.shared.handlers

import com.jeferro.lib.domain.handlers.BaseHandler
import com.jeferro.lib.domain.handlers.HandlerBus
import com.jeferro.lib.domain.handlers.HandlerParams
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import java.lang.reflect.ParameterizedType

@Component
class SpringHandlerBus(
    applicationContext: ApplicationContext
) : HandlerBus {
    private val handlers = mutableMapOf<Class<out HandlerParams<*>>, BaseHandler<*, *>?>()

    init {
        applicationContext.getBeansOfType(BaseHandler::class.java)
            .values
            .forEach { handler -> registryHandler(handler) }
    }

    override suspend fun <R> execute(params: HandlerParams<R>): R {
        val handler = getHandler(params)

        return handler.execute(params)
    }

    @Suppress("UNCHECKED_CAST")
    private fun registryHandler(handler: BaseHandler<*, *>) {
        val type = handler.javaClass.genericSuperclass

        if (type !is ParameterizedType) {
            throw IllegalArgumentException("Handler superclass is not a parameterized type")
        }

        val paramsClass = type.actualTypeArguments[0] as Class<out HandlerParams<*>>

        handlers[paramsClass] = handler
    }

    @Suppress("UNCHECKED_CAST")
    private fun <R> getHandler(params: HandlerParams<R>): BaseHandler<HandlerParams<R>, R> {
        val paramsClass = params.javaClass

        val handler = handlers[paramsClass]
            ?: throw HandlerNotFoundException.create(paramsClass as Class<HandlerParams<*>>)

        return handler as BaseHandler<HandlerParams<R>, R>
    }
}