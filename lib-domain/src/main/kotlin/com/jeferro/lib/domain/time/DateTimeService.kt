package com.jeferro.lib.domain.time

import java.time.Instant

class DateTimeService : TimeService() {

    override fun now(): Instant {
        return Instant.now()
    }
}
