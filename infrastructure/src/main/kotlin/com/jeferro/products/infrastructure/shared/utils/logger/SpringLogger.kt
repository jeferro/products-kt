package com.jeferro.products.infrastructure.shared.utils.logger

import org.slf4j.LoggerFactory
import kotlin.reflect.KClass
import com.jeferro.products.domain.shared.utils.logger.Logger as DomainLogger

class SpringLogger(
    clazz: KClass<out Any>
) : DomainLogger {

    private val logger = LoggerFactory.getLogger(clazz.java)

    override fun debug(message: String) {
        logger.debug(message)
    }

    override fun info(message: String) {
        logger.info(message)
    }

    override fun warn(message: String) {
        logger.warn(message)
    }

    override fun warn(message: String, cause: Exception?) {
        logger.warn(message, cause)
    }

    override fun error(message: String) {
        logger.error(message)
    }

    override fun error(message: String, cause: Exception?) {
        logger.error(message, cause)
    }
}