package com.jeferro.lib.infrastructure.shared.domain

import com.jeferro.lib.infrastructure.shared.domain.events.SpringEventBus
import com.jeferro.lib.infrastructure.shared.application.SpringHandlerBus
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType
import org.springframework.context.annotation.Import

@Configuration
// TODO: Set base package as parameter
@ComponentScan(
    basePackages = ["com.jeferro"],
    includeFilters = [
        ComponentScan.Filter(
            type = FilterType.CUSTOM,
            classes = [DomainComponentScanFilter::class]
        )
    ]
)
@Import(
    value = [
        SpringHandlerBus::class,
        SpringEventBus::class]
)
class DomainConfiguration {

}
