package com.jeferro.lib.infrastructure.shared.application

import com.jeferro.lib.application.BaseHandler
import com.jeferro.lib.application.bus.HandlerBus
import org.springframework.context.ApplicationContext

class SpringHandlerBus(
    applicationContext: ApplicationContext
) : HandlerBus() {

    init {
        applicationContext.getBeansOfType(BaseHandler::class.java)
            .values
            .forEach { handler -> registryHandler(handler) }
    }
}