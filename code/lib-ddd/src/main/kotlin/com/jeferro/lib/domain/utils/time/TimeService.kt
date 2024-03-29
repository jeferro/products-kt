package com.jeferro.lib.domain.utils.time

import java.time.Instant

abstract class TimeService {

    abstract fun now(): Instant

    companion object {
        private var instance: TimeService = InstantTimeService()

        fun now(): Instant {
            return instance.now()
        }

        @JvmStatic
        protected fun configure(instance: TimeService) {
            Companion.instance = instance
        }
    }

}
