package com.jeferro.products.domain.shared.utils.time

import java.time.Instant

class DateTimeService : TimeService() {

    override fun now(): Instant {
        return Instant.now()
    }
}
