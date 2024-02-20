package com.jeferro.products.products.domain.exceptions

import com.jeferro.lib.domain.exceptions.NotFoundException
import com.jeferro.products.products.domain.models.ProductId

class ProductNotFoundException(
    message: String
) : NotFoundException(message) {

    companion object {
        fun create(productId: ProductId): ProductNotFoundException {
            return ProductNotFoundException("Product '$productId' not found")
        }
    }
}