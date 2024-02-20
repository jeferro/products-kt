package com.jeferro.products.products.domain.handlers

import com.jeferro.lib.domain.events.EventBus
import com.jeferro.lib.domain.handlers.CommandHandler
import com.jeferro.lib.domain.handlers.Handler
import com.jeferro.products.products.domain.handlers.operations.DeleteProduct
import com.jeferro.products.products.domain.models.Product
import com.jeferro.products.products.domain.repositories.ProductsRepository
import com.jeferro.products.products.domain.services.ProductFinder
import com.jeferro.products.shared.Roles.USER

@Handler
class DeleteProductHandler(
    private val productFinder: ProductFinder,
    private val productsRepository: ProductsRepository,
    private val eventBus: EventBus
) : CommandHandler<DeleteProduct, Product>() {

    override val mandatoryRoles: List<String>
        get() = arrayListOf(USER)

    override suspend fun handle(operation: DeleteProduct): Product {
        val productId = operation.productId

        val product = productFinder.findById(productId)

        product.delete(
            operation.userId
        )

        productsRepository.deleteById(productId)

        eventBus.publishAll(product)

        return product
    }

}