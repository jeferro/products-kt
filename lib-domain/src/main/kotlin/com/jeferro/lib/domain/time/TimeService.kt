package com.jeferro.lib.domain.time

import java.time.Instant
import java.time.temporal.ChronoUnit

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
