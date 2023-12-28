package com.jeferro.products.domain.product_reviews.services

import com.jeferro.products.domain.product_reviews.entities.ProductReview
import com.jeferro.products.domain.product_reviews.entities.ProductReviewId
import com.jeferro.products.domain.product_reviews.exceptions.ProductReviewNotFoundException.Companion.productReviewNotFoundExceptionOf
import com.jeferro.products.domain.product_reviews.repositories.ProductReviewsRepository
import com.jeferro.products.domain.shared.services.DomainService

@DomainService
class ProductReviewFinder(
    private val productReviewsRepository: ProductReviewsRepository
) {
    suspend fun findById(productReviewId: ProductReviewId): ProductReview {
        return productReviewsRepository.findById(productReviewId)
            ?: throw productReviewNotFoundExceptionOf(productReviewId)
    }
}