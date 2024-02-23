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
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

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
class WebSecurityConfiguration : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
    }

    @Bean
    fun userDetailsService(): UserDetailsService? {
        return InMemoryUserDetailsManager()
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
