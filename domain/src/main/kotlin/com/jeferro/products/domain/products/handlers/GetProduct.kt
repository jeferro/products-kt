package com.jeferro.products.domain.products.handlers

import com.jeferro.products.domain.Roles.USER
import com.jeferro.products.domain.products.entities.Product
import com.jeferro.products.domain.products.handlers.params.GetProductParams
import com.jeferro.products.domain.products.services.ProductFinder
import com.jeferro.products.domain.shared.handlers.Handler
import com.jeferro.products.domain.shared.handlers.QueryHandler

@Handler
class GetProduct(
    private val productFinder: ProductFinder
) : QueryHandler<GetProductParams, Product>() {

    override val mandatoryRoles: List<String>
        get() = arrayListOf(USER)

    override suspend fun handle(params: GetProductParams): Product {
        return productFinder.findById(params.productId)
    }

}