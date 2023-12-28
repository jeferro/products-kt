package com.jeferro.products.domain.shared.services.time

import com.jeferro.products.domain.shared.utils.time.TimeService
import java.time.Instant
import java.time.temporal.ChronoUnit

class FakeTimeService(
    private val dates: List<Instant>
) : TimeService() {

    companion object {

        fun timeServiceFakesDates(vararg dates: Instant) {
            val fakeTimeService = FakeTimeService(dates.toList())

            configure(fakeTimeService)
        }

        fun timeServiceFakesNow(): Instant {
            val now = Instant.now()

            timeServiceFakesDates(now)

            return now
        }

        fun timeServiceFakesOneMinuteLater(): Instant {
            val oneMinuteLater = Instant.now()
                .plus(1, ChronoUnit.MINUTES)

            timeServiceFakesDates(oneMinuteLater)

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
