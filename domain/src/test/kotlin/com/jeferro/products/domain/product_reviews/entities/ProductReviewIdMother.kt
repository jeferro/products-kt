package com.jeferro.products.domain.product_reviews.entities

import com.jeferro.products.domain.products.entities.ProductIdMother.oneProductId
import com.jeferro.products.domain.shared.entities.auth.UserId
import com.jeferro.products.domain.shared.entities.auth.UserIdMother.oneUserId

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