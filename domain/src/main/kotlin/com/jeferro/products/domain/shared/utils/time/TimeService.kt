package com.jeferro.products.domain.shared.utils.time

import java.time.Instant

abstract class TimeService {

    abstract fun now(): Instant

    companion object {
        private var instance: TimeService = DateTimeService()

        fun now(): Instant {
            return instance.now()
        }

        @JvmStatic
        protected fun configure(instance: TimeService) {
            Companion.instance = instance
        }
    }

}
