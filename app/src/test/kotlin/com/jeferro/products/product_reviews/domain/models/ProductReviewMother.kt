package com.jeferro.products.product_reviews.domain.models

import com.jeferro.lib.domain.models.auth.UserId
import com.jeferro.products.product_reviews.domain.models.ProductReviewIdMother.oneProductReviewId
import com.jeferro.products.shared.domain.models.metadata.MetadataMother.oneMetadata

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