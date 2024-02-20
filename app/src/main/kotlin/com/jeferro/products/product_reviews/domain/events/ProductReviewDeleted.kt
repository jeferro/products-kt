package com.jeferro.products.product_reviews.domain.events

import com.jeferro.lib.domain.events.DomainEvent
import com.jeferro.lib.domain.models.auth.UserId
import com.jeferro.products.product_reviews.domain.models.ProductReview
import com.jeferro.products.product_reviews.domain.models.ProductReviewId
import java.time.Instant

class ProductReviewDeleted(
    val productReviewId: ProductReviewId,
    occurredBy: UserId,
    occurredOn: Instant
) : DomainEvent(occurredBy, occurredOn) {

    companion object {
        fun create(productReview: ProductReview): ProductReviewDeleted {
            return ProductReviewDeleted(
                productReview.id,
                productReview.updatedBy,
                productReview.updatedAt
            )
        }
    }
}
