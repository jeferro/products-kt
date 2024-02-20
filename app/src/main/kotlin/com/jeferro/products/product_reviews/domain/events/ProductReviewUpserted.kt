package com.jeferro.products.product_reviews.domain.events

import com.jeferro.lib.domain.events.DomainEvent
import com.jeferro.lib.domain.models.auth.UserId
import com.jeferro.products.product_reviews.domain.models.ProductReview
import com.jeferro.products.product_reviews.domain.models.ProductReviewId
import java.time.Instant

class ProductReviewUpserted(
    val productReviewId: ProductReviewId,
    occurredBy: UserId,
    occurredOn: Instant
) : DomainEvent(occurredBy, occurredOn) {

    companion object {
        fun create(productReview: ProductReview): ProductReviewUpserted {
            return ProductReviewUpserted(
                productReview.id,
                productReview.createdBy,
                productReview.createdAt
            )
        }
    }
}
