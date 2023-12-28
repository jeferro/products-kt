package com.jeferro.products.domain.products.repositories

import com.jeferro.products.domain.products.entities.Product
import com.jeferro.products.domain.products.entities.ProductId
import com.jeferro.products.domain.products.entities.Products

interface ProductsRepository {

    suspend fun save(product: Product)

    suspend fun findById(productId: ProductId): Product?

    suspend fun deleteById(productId: ProductId)

    suspend fun findAll(): Products
}