package com.jeferro.lib.infrastructure.shared.security.jwt

import org.springframework.beans.factory.annotation.Value
import java.time.Duration


class JwtProperties{
    @Value("\${components.security.jwt.issuer}")
    lateinit var issuer: String

    @Value("\${components.security.jwt.password}")
    lateinit var password: String

    @Value("\${components.security.jwt.duration}")
    lateinit var duration: Duration

    val durationInMillis
        get() = duration.toMillis()
}