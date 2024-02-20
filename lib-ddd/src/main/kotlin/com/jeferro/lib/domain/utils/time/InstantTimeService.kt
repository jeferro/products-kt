package com.jeferro.lib.domain.utils.time

import java.time.Instant
import java.time.temporal.ChronoUnit

class InstantTimeService : TimeService() {

    override fun now(): Instant {
        /*
            * TODO: Mongo date precision
            *  We truncate millis because mongo dates have a millis precision
            */
        return Instant.now()
            .truncatedTo(ChronoUnit.MILLIS)
    }
}
