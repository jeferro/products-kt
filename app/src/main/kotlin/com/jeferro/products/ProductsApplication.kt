package com.jeferro.products

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication(
    scanBasePackages = ["com.jeferro"]
)
@ConfigurationPropertiesScan(
    basePackages = ["com.jeferro"]
)
@EnableMongoRepositories
class ProductsApplication

fun main(args: Array<String>) {
    runApplication<ProductsApplication>(*args)
}
