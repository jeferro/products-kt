package com.jeferro.lib.infrastructure.shared.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "components.security.jwt")
class JwtProperties(
    var issuer: String,
    var password: String,
    var duration: Duration
) {
    val durationInMillis
        get() = duration.toMillis()
}