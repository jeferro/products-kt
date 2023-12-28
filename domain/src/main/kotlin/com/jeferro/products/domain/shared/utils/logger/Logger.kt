package com.jeferro.products.domain.shared.utils.logger

import kotlin.reflect.KClass

interface Logger {

    companion object {
        fun loggerOf(clazz: KClass<out Any>): Logger {
            return LoggerCreator.create(clazz)
        }
    }

    fun debug(message: String)

    fun info(message: String)

    fun warn(message: String)

    fun warn(message: String, cause: Exception?)

    fun error(message: String)

    fun error(message: String, cause: Exception?)

}