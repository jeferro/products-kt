package com.jeferro.lib.domain.time

import java.time.Instant
import java.time.temporal.ChronoUnit

abstract class TimeService {

    abstract fun now(): Instant

    companion object {
        private var instance: TimeService = DateTimeService()

        fun now(): Instant {
            /*
             * TODO: Mongo date precision
             *  We truncate millis because mongo dates have a millis precision
             */
            return instance.now()
                .truncatedTo(ChronoUnit.MILLIS)
        }

        @JvmStatic
        protected fun configure(instance: TimeService) {
            Companion.instance = instance
        }
    }

}
