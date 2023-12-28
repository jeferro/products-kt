package com.jeferro.products.domain.product_reviews.entities

import com.jeferro.products.domain.product_reviews.entities.ProductReviewIdMother.oneProductReviewId
import com.jeferro.products.domain.shared.entities.auth.UserId
import com.jeferro.products.domain.shared.entities.metadata.MetadataMother.oneMetadata

object ProductReviewMother {

    fun oneProductReview(): ProductReview {
        val oneMetadata = oneMetadata()

        return ProductReview(
            oneProductReviewId(),
            "comment of product",
            oneMetadata
        )
    }

    fun oneProductReview(userId: UserId): ProductReview {
        val oneMetadata = oneMetadata()

        return ProductReview(
            oneProductReviewId(userId),
            "comment of product",
            oneMetadata
        )
    }
}