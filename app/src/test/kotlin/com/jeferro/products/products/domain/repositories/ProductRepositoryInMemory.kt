package com.jeferro.products.products.domain.repositories

import com.jeferro.products.domain.products.entities.Products
import com.jeferro.products.products.domain.models.Product
import com.jeferro.products.products.domain.models.ProductId
import com.jeferro.products.shared.domain.repositories.RepositoryInMemory

class ProductRepositoryInMemory
    : ProductsRepository,
    RepositoryInMemory<ProductId, Product>() {

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