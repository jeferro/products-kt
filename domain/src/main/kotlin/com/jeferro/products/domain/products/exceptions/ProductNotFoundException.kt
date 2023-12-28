package com.jeferro.products.domain.products.exceptions

import com.jeferro.products.domain.products.entities.ProductId
import com.jeferro.products.domain.shared.exceptions.NotFoundException

class ProductNotFoundException(
    message: String
) : NotFoundException(message) {

    companion object {
        fun productNotFoundExceptionOf(productId: ProductId): ProductNotFoundException {
            return ProductNotFoundException("Product '$productId' not found")
        }
    }
}