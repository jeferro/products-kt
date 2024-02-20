package com.jeferro.products.products.application

import com.jeferro.lib.domain.events.bus.EventBus
import com.jeferro.lib.application.CommandHandler
import com.jeferro.lib.application.Handler
import com.jeferro.products.products.application.operations.ChangeProductActivation
import com.jeferro.products.products.domain.models.Product
import com.jeferro.products.products.domain.repositories.ProductsRepository
import com.jeferro.products.products.domain.services.ProductFinder
import com.jeferro.products.shared.Roles.USER

@Handler
class ChangeProductActivationHandler(
    private val productFinder: ProductFinder,
    private val productsRepository: ProductsRepository,
    private val eventBus: EventBus
) : CommandHandler<ChangeProductActivation, Product>() {

    override val mandatoryRoles: List<String>
        get() = arrayListOf(USER)

    override suspend fun handle(operation: ChangeProductActivation): Product {
        val productId = operation.productId

        val product = productFinder.findById(productId)

        product.changeActivation(
            operation.isEnabled,
            operation.userId
        )

        productsRepository.save(product)

        eventBus.publishAll(product)

        return product
    }

}