package com.jeferro.lib.infrastructure.shared.security

import com.jeferro.lib.infrastructure.shared.security.jwt.JwtEncoder
import com.jeferro.lib.infrastructure.shared.security.jwt.JwtProperties
import com.jeferro.lib.infrastructure.shared.security.jwt.JwtRequestFilter
import com.jeferro.lib.infrastructure.shared.security.services.AuthRestService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@Import(
    value = [
        JwtProperties::class,
        JwtEncoder::class,
        JwtRequestFilter::class,
        AuthRestService::class,
    ]
)
@EnableWebSecurity
class WebSecurityConfiguration {

    @Bean
    fun userDetailsService(): UserDetailsService? {
        return InMemoryUserDetailsManager()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("*")
        configuration.allowedHeaders = listOf("*")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtRequestFilter: JwtRequestFilter
    ): SecurityFilterChain {
        return http.csrf { csrf -> csrf.disable() }
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { authorize -> authorize.anyRequest().permitAll() }
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }
}
