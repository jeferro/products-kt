package com.jeferro.lib.domain.time

import java.time.Instant
import java.time.temporal.ChronoUnit

class FakeTimeService private constructor(
    private val dates: List<Instant>
) : TimeService() {

    companion object {

        fun fakesDates(vararg dates: Instant) {
            val fakeTimeService = FakeTimeService(dates.toList())

            configure(fakeTimeService)
        }

        fun fakesNow(): Instant {
            /*
             * TODO: Mongo date precision
             *  We truncate millis because mongo dates have a millis precision
             */
            val now = Instant.now()
                .truncatedTo(ChronoUnit.MILLIS)

            fakesDates(now)

            return now
        }

        fun fakesOneMinuteLater(): Instant {
            val oneMinuteLater = Instant.now()
                .plus(1, ChronoUnit.MINUTES)

            fakesDates(oneMinuteLater)

            return oneMinuteLater
        }
    }

    private var current = 0

    override fun now(): Instant {
        val now = dates[current]

        current += 1

        if (current >= dates.size) {
            current = 0
        }

        return now
    }
}