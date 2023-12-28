package com.jeferro.products.domain.shared.services.logger

import com.jeferro.products.domain.shared.utils.logger.Logger
import com.jeferro.products.domain.shared.utils.logger.LoggerCreator
import kotlin.reflect.KClass

class FakeLoggerCreator : LoggerCreator {

    companion object {

        fun configureFakeLogger() {
            val fakeLoggerCreator = FakeLoggerCreator()

            LoggerCreator.configure(fakeLoggerCreator)
        }
    }

    override fun create(clazz: KClass<out Any>): Logger {
        return FakeLogger()
    }
}