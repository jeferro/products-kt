package com.jeferro.products.products.domain.repositories

import com.jeferro.products.products.domain.models.Product
import com.jeferro.products.products.domain.models.ProductId
import com.jeferro.products.domain.products.entities.Products

interface ProductsRepository {

    suspend fun save(product: Product)

    suspend fun findById(productId: ProductId): Product?

    suspend fun deleteById(productId: ProductId)

    suspend fun findAll(): Products
}