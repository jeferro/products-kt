package com.jeferro.products.product_reviews.domain.repositories

import com.jeferro.products.product_reviews.domain.models.ProductReview
import com.jeferro.products.product_reviews.domain.models.ProductReviewId
import com.jeferro.products.shared.domain.repositories.RepositoryInMemory

class ProductReviewsRepositoryInMemory
    : ProductReviewsRepository,
    RepositoryInMemory<ProductReviewId, ProductReview>() {

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