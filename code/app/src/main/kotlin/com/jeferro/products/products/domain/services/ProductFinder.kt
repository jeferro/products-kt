package com.jeferro.products.products.domain.services

import com.jeferro.lib.domain.services.DomainService
import com.jeferro.products.products.domain.exceptions.ProductNotFoundException
import com.jeferro.products.products.domain.models.Product
import com.jeferro.products.products.domain.models.ProductId
import com.jeferro.products.products.domain.repositories.ProductsRepository

@DomainService
class ProductFinder(
    private val productsRepository: ProductsRepository
) {
    suspend fun findById(productId: ProductId): Product {
        return productsRepository.findById(productId)
            ?: throw ProductNotFoundException.create(productId)
    }
}