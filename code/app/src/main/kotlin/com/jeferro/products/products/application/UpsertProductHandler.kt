package com.jeferro.products.products.application

import com.jeferro.lib.domain.events.bus.EventBus
import com.jeferro.lib.application.CommandHandler
import com.jeferro.lib.application.Handler
import com.jeferro.products.products.application.operations.UpsertProduct
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
                operation.authId
            )
        } else {
            product.update(
                operation.title,
                operation.description,
                operation.authId
            )
        }

        productsRepository.save(product)

        eventBus.publishAll(product)

        return product
    }

}