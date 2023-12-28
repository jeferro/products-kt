package com.jeferro.products.infrastructure.shared.handlers.bus

import com.jeferro.products.domain.shared.handlers.BaseHandler
import com.jeferro.products.domain.shared.handlers.bus.HandlerBus
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class SpringHandlerBus(
    applicationContext: ApplicationContext
) : HandlerBus() {

    init {
        applicationContext.getBeansOfType(BaseHandler::class.java)
            .values
            .forEach { handler -> registryHandler(handler) }
    }

}