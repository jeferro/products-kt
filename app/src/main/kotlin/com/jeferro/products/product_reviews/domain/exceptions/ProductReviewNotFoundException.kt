package com.jeferro.products.product_reviews.domain.exceptions

import com.jeferro.lib.domain.exceptions.NotFoundException
import com.jeferro.products.product_reviews.domain.models.ProductReviewId

class ProductReviewNotFoundException(
    message: String
) : NotFoundException(message) {

    companion object {
        fun create(productReviewId: ProductReviewId): ProductReviewNotFoundException {
            return ProductReviewNotFoundException("Product review '$productReviewId' not found")
        }
    }
}