package com.jeferro.products.product_reviews.domain.repositories

import com.jeferro.products.product_reviews.domain.models.ProductReview
import com.jeferro.products.product_reviews.domain.models.ProductReviewId

interface ProductReviewsRepository {

    suspend fun save(productReview: ProductReview)

    suspend fun findById(productReviewId: ProductReviewId): ProductReview?

    suspend fun deleteById(productReviewId: ProductReviewId)
}