package com.jeferro.products.domain.products.repositories

import com.jeferro.products.domain.products.entities.Product
import com.jeferro.products.domain.products.entities.ProductId
import com.jeferro.products.domain.products.entities.Products
import com.jeferro.products.domain.shared.repositories.InMemoryRepository

class ProductInMemoryRepository
    : ProductsRepository,
    InMemoryRepository<ProductId, Product>() {

    override suspend fun save(product: Product) {
        data[product.id] = product
    }

    override suspend fun findById(productId: ProductId): Product? {
        return data[productId]
    }

    override suspend fun deleteById(productId: ProductId) {
        data.remove(productId)
    }

    override suspend fun findAll(): Products {
        return Products.of(data.values)
    }
}