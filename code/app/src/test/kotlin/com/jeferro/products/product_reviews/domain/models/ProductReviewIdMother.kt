package com.jeferro.products.product_reviews.domain.models

import com.jeferro.lib.domain.models.auth.UserId
import com.jeferro.products.products.domain.models.ProductIdMother.oneProductId
import com.jeferro.products.shared.domain.models.auth.UserIdMother.oneUserId

object ProductReviewIdMother {

    fun oneProductReviewId(): ProductReviewId {
        val oneProductId = oneProductId()
        val oneUserId = oneUserId()

        return ProductReviewId(
            oneProductId,
            oneUserId
        )
    }

    fun oneProductReviewId(userId: UserId): ProductReviewId {
        val oneProductId = oneProductId()

        return ProductReviewId(
            oneProductId,
            userId
        )
    }
}