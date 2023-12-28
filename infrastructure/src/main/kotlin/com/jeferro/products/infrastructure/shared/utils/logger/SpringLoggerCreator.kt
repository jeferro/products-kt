package com.jeferro.products.infrastructure.shared.utils.logger

import com.jeferro.products.domain.shared.utils.logger.Logger
import com.jeferro.products.domain.shared.utils.logger.LoggerCreator
import kotlin.reflect.KClass

class SpringLoggerCreator : LoggerCreator {
    override fun create(clazz: KClass<out Any>): Logger {
        return SpringLogger(clazz)
    }
}