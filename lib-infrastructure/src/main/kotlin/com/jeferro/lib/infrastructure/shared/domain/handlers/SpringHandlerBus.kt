package com.jeferro.lib.infrastructure.shared.domain.handlers

import com.jeferro.lib.domain.handlers.BaseHandler
import com.jeferro.lib.domain.handlers.bus.HandlerBus
import com.jeferro.lib.domain.handlers.Operation
import org.springframework.context.ApplicationContext
import java.lang.reflect.ParameterizedType

class SpringHandlerBus(
    applicationContext: ApplicationContext
) : HandlerBus {
    private val handlers = mutableMapOf<Class<out Operation<*>>, BaseHandler<*, *>?>()

    init {
        applicationContext.getBeansOfType(BaseHandler::class.java)
            .values
            .forEach { handler -> registryHandler(handler) }
    }

    override suspend fun <R> execute(operation: Operation<R>): R {
        val handler = getHandler(operation)

        return handler.execute(operation)
    }

    @Suppress("UNCHECKED_CAST")
    private fun registryHandler(handler: BaseHandler<*, *>) {
        val type = handler.javaClass.genericSuperclass

        if (type !is ParameterizedType) {
            throw IllegalArgumentException("Handler superclass is not a parameterized type")
        }

        val operationClass = type.actualTypeArguments[0] as Class<out Operation<*>>

        handlers[operationClass] = handler
    }

    @Suppress("UNCHECKED_CAST")
    private fun <R> getHandler(operation: Operation<R>): BaseHandler<Operation<R>, R> {
        val operationClass = operation.javaClass

        val handler = handlers[operationClass]
            ?: throw HandlerNotFoundException.create(operationClass as Class<Operation<*>>)

        return handler as BaseHandler<Operation<R>, R>
    }
}