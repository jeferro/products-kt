package com.jeferro.products.domain.products.handlers

import com.jeferro.products.domain.Roles.USER
import com.jeferro.products.domain.products.entities.Product
import com.jeferro.products.domain.products.handlers.params.DeleteProductParams
import com.jeferro.products.domain.products.repositories.ProductsRepository
import com.jeferro.products.domain.products.services.ProductFinder
import com.jeferro.products.domain.shared.events.bus.EventBus
import com.jeferro.products.domain.shared.handlers.CommandHandler
import com.jeferro.products.domain.shared.handlers.Handler

@Handler
class DeleteProduct(
    private val productFinder: ProductFinder,
    private val productsRepository: ProductsRepository,
    private val eventBus: EventBus
) : CommandHandler<DeleteProductParams, Product>() {

    override val mandatoryRoles: List<String>
        get() = arrayListOf(USER)

    override suspend fun handle(params: DeleteProductParams): Product {
        val productId = params.productId

        val product = productFinder.findById(productId)

        product.delete(
            params.userId
        )

        productsRepository.deleteById(productId)

        eventBus.publishOf(product)

        return product
    }

}