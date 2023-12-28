package com.jeferro.products

import com.jeferro.products.domain.shared.utils.logger.LoggerCreator
import com.jeferro.products.infrastructure.shared.utils.component_scan.DomainComponentScan
import com.jeferro.products.infrastructure.shared.utils.logger.SpringLoggerCreator
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
@DomainComponentScan
class ProductsApplication

fun main(args: Array<String>) {
    val springLoggerCreator = SpringLoggerCreator()
    LoggerCreator.configure(springLoggerCreator)

    runApplication<ProductsApplication>(*args)
}
