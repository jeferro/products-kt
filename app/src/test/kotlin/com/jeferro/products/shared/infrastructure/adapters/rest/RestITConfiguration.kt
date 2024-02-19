package com.jeferro.products.shared.infrastructure.adapters.rest

import com.jeferro.lib.infrastructure.shared.security.AuthRestService
import com.jeferro.products.shared.infrastructure.configurations.SecurityConfiguration
import com.jeferro.lib.infrastructure.shared.security.jwt.JwtEncoder
import com.jeferro.lib.infrastructure.shared.security.jwt.JwtProperties
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import java.time.Duration

@Import(
    value = [
        AuthRestService::class,
        JwtEncoder::class,
        SecurityConfiguration::class
    ]
)
@AutoConfigureMockMvc
class RestITConfiguration {

    @Bean
    fun jwtProperties(): JwtProperties {
        return JwtProperties(
            "",
            "aa",
            Duration.ofMinutes(60)
        )
    }
}