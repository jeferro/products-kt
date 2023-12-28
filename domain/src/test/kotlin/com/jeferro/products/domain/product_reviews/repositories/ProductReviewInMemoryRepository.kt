package com.jeferro.products.domain.product_reviews.repositories

import com.jeferro.products.domain.product_reviews.entities.ProductReview
import com.jeferro.products.domain.product_reviews.entities.ProductReviewId
import com.jeferro.products.domain.shared.repositories.InMemoryRepository

class ProductReviewInMemoryRepository
    : ProductReviewsRepository,
    InMemoryRepository<ProductReviewId, ProductReview>() {

    override suspend fun save(productReview: ProductReview) {
        data[productReview.id] = productReview
    }

    override suspend fun findById(productReviewId: ProductReviewId): ProductReview? {
        return data[productReviewId]
    }

    override suspend fun deleteById(productReviewId: ProductReviewId) {
        data.remove(productReviewId)
    }
}