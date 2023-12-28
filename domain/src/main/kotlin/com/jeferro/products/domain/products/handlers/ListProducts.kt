package com.jeferro.products.domain.products.handlers

import com.jeferro.products.domain.Roles.USER
import com.jeferro.products.domain.products.entities.Products
import com.jeferro.products.domain.products.handlers.params.ListProductsParams
import com.jeferro.products.domain.products.repositories.ProductsRepository
import com.jeferro.products.domain.shared.handlers.Handler
import com.jeferro.products.domain.shared.handlers.QueryHandler

@Handler
class ListProducts(
    private val productsRepository: ProductsRepository
) : QueryHandler<ListProductsParams, Products>() {

    override val mandatoryRoles: List<String>
        get() = arrayListOf(USER)

    override suspend fun handle(params: ListProductsParams): Products {
        return productsRepository.findAll()
    }

}