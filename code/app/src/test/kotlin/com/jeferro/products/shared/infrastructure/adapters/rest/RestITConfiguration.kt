package com.jeferro.products.shared.infrastructure.adapters.rest

import com.jeferro.lib.infrastructure.shared.security.WebSecurityConfiguration
import com.jeferro.lib.infrastructure.shared.security.jwt.JwtProperties
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import java.time.Duration

@Import(
    value = [
        WebSecurityConfiguration::class
    ]
)
@AutoConfigureMockMvc
class RestITConfiguration {

    @Bean
    fun jwtProperties(): JwtProperties {
        val jwtProperties = JwtProperties()
        jwtProperties.issuer = ""
        jwtProperties.password = "aa"
        jwtProperties.duration = Duration.ofMinutes(60)

        return jwtProperties
    }
}