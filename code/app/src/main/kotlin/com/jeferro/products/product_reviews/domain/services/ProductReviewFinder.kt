package com.jeferro.products.product_reviews.domain.services

import com.jeferro.lib.domain.services.DomainService
import com.jeferro.products.product_reviews.domain.exceptions.ProductReviewNotFoundException
import com.jeferro.products.product_reviews.domain.models.ProductReview
import com.jeferro.products.product_reviews.domain.models.ProductReviewId
import com.jeferro.products.product_reviews.domain.repositories.ProductReviewsRepository

@DomainService
class ProductReviewFinder(
    private val productReviewsRepository: ProductReviewsRepository
) {
    suspend fun findById(productReviewId: ProductReviewId): ProductReview {
        return productReviewsRepository.findById(productReviewId)
            ?: throw ProductReviewNotFoundException.create(productReviewId)
    }
}