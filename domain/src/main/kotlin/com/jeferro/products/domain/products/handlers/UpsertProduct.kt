package com.jeferro.products.domain.products.handlers

import com.jeferro.products.domain.Roles.USER
import com.jeferro.products.domain.products.entities.Product
import com.jeferro.products.domain.products.handlers.params.UpsertProductParams
import com.jeferro.products.domain.products.repositories.ProductsRepository
import com.jeferro.products.domain.shared.events.bus.EventBus
import com.jeferro.products.domain.shared.handlers.CommandHandler
import com.jeferro.products.domain.shared.handlers.Handler

@Handler
class UpsertProduct(
    private val productsRepository: ProductsRepository,
    private val eventBus: EventBus
) : CommandHandler<UpsertProductParams, Product>() {

    override val mandatoryRoles: List<String>
        get() = arrayListOf(USER)

    override suspend fun handle(params: UpsertProductParams): Product {
        val productId = params.productId

        var product = productsRepository.findById(productId)

        if( product == null) {
            product = Product.productOf(
                productId,
                params.title,
                params.description,
                params.userId
            )
        }
        else {
            product.update(
                params.title,
                params.description,
                params.userId
            )
        }

        productsRepository.save(product)

        eventBus.publishOf(product)

        return product
    }

}