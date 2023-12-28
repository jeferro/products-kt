package com.jeferro.products.domain.product_reviews.exceptions

import com.jeferro.products.domain.product_reviews.entities.ProductReviewId
import com.jeferro.products.domain.shared.exceptions.NotFoundException

class ProductReviewNotFoundException(
    message: String
) : NotFoundException(message) {

    companion object {
        fun productReviewNotFoundExceptionOf(productReviewId: ProductReviewId): ProductReviewNotFoundException {
            return ProductReviewNotFoundException("Product review '$productReviewId' not found")
        }
    }
}