package com.jeferro.products.domain.products.services

import com.jeferro.products.domain.products.entities.Product
import com.jeferro.products.domain.products.entities.ProductId
import com.jeferro.products.domain.products.exceptions.ProductNotFoundException
import com.jeferro.products.domain.products.repositories.ProductsRepository
import com.jeferro.products.domain.shared.services.DomainService

@DomainService
class ProductFinder(
    private val productsRepository: ProductsRepository
) {
    suspend fun findById(productId: ProductId): Product {
        return productsRepository.findById(productId)
            ?: throw ProductNotFoundException.productNotFoundExceptionOf(productId)
    }
}