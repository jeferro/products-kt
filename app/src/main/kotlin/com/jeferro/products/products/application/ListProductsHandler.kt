package com.jeferro.products.products.application

import com.jeferro.lib.application.Handler
import com.jeferro.lib.application.QueryHandler
import com.jeferro.products.domain.products.entities.Products
import com.jeferro.products.products.application.operations.ListProducts
import com.jeferro.products.products.domain.repositories.ProductsRepository
import com.jeferro.products.shared.Roles.USER

@Handler
class ListProductsHandler(
    private val productsRepository: ProductsRepository
) : QueryHandler<ListProducts, Products>() {

    override val mandatoryRoles: List<String>
        get() = arrayListOf(USER)

    override suspend fun handle(operation: ListProducts): Products {
        return productsRepository.findAll()
    }
}
