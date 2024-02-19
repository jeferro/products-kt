package com.jeferro.lib.infrastructure.shared.scan

import com.jeferro.products.infrastructure.shared.utils.component_scan.DomainComponentScanFilter
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType

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
annotation class DomainComponentScan()
