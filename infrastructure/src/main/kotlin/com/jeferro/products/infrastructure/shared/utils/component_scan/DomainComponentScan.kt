package com.jeferro.products.infrastructure.shared.utils.component_scan

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType

@ComponentScan(
    basePackages = ["com.jeferro"],
    includeFilters = [
        ComponentScan.Filter(
            type = FilterType.CUSTOM,
            classes = [DomainComponentScanFilter::class]
        )
    ]
)
annotation class DomainComponentScan()
