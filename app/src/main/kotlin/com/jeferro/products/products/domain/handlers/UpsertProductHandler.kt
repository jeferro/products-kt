package com.jeferro.products.products.domain.handlers

import com.jeferro.lib.domain.events.EventBus
import com.jeferro.lib.domain.handlers.CommandHandler
import com.jeferro.lib.domain.handlers.Handler
import com.jeferro.products.products.domain.handlers.operations.UpsertProduct
import com.jeferro.products.products.domain.models.Product
import com.jeferro.products.products.domain.repositories.ProductsRepository
import com.jeferro.products.shared.Roles

@Handler
class UpsertProductHandler(
    private val productsRepository: ProductsRepository,
    private val eventBus: EventBus
) : CommandHandler<UpsertProduct, Product>() {

    override val mandatoryRoles: List<String>
        get() = arrayListOf(Roles.USER)

    override suspend fun handle(operation: UpsertProduct): Product {
        val productId = operation.productId

        var product = productsRepository.findById(productId)

        if (product == null) {
            product = Product.create(
                productId,
                operation.title,
                operation.description,
                operation.userId
            )
        } else {
            product.update(
                operation.title,
                operation.description,
                operation.userId
            )
        }

        productsRepository.save(product)

        eventBus.publishAll(product)

        return product
    }

}