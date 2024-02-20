package com.jeferro.lib.infrastructure.shared

import com.jeferro.lib.infrastructure.shared.domain.DomainConfiguration
import com.jeferro.lib.infrastructure.shared.security.WebSecurityConfiguration
import org.springframework.context.annotation.Import


@Import(
    value = [
        DomainConfiguration::class,
        WebSecurityConfiguration::class
    ]
)
annotation class EnableJeferroApplication
