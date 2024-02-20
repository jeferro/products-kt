package com.jeferro.products.products.domain.handlers

import com.jeferro.lib.domain.handlers.Handler
import com.jeferro.lib.domain.handlers.QueryHandler
import com.jeferro.products.products.domain.handlers.operations.GetProduct
import com.jeferro.products.products.domain.models.Product
import com.jeferro.products.products.domain.services.ProductFinder
import com.jeferro.products.shared.Roles.USER

@Handler
class GetProductHandler(
    private val productFinder: ProductFinder
) : QueryHandler<GetProduct, Product>() {

    override val mandatoryRoles: List<String>
        get() = arrayListOf(USER)

    override suspend fun handle(operation: GetProduct): Product {
        return productFinder.findById(operation.productId)
    }

}