package com.jeferro.products.infrastructure.shared.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "components.security.jwt")
data class JwtProperties(
    val issuer: String,
    val password: String,
    val duration: Duration,
) {
    val durationInMillis = duration.toMillis()
}