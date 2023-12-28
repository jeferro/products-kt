package com.jeferro.products.domain.product_reviews.repositories

import com.jeferro.products.domain.product_reviews.entities.ProductReview
import com.jeferro.products.domain.product_reviews.entities.ProductReviewId

interface ProductReviewsRepository {

    suspend fun save(productReview: ProductReview)

    suspend fun findById(productReviewId: ProductReviewId): ProductReview?

    suspend fun deleteById(productReviewId: ProductReviewId)
}