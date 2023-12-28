package com.jeferro.products.domain.shared.utils.logger

import com.jeferro.products.domain.shared.exceptions.InternalException
import kotlin.reflect.KClass

interface LoggerCreator {

    companion object {
        private var instance: LoggerCreator? = null

        fun configure(instance: LoggerCreator) {
            Companion.instance = instance
        }

        fun create(clazz: KClass<out Any>): Logger {
            if (instance == null) {
                throw InternalException("Logger create is not configured", null)
            }

            return instance!!.create(clazz)
        }
    }

    fun create(clazz: KClass<out Any>): Logger
}