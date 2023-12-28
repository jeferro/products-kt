package com.jeferro.products.infrastructure.shared.security.jwt

import com.jeferro.products.infrastructure.shared.security.jwt.JwtProperties
import java.time.Duration
import java.time.temporal.ChronoUnit.DAYS

object JwtPropertiesMother {

    fun oneJwtProperties(): JwtProperties {
        return JwtProperties(
            "app",
            "password",
            Duration.of(1, DAYS)
        )
    }
}