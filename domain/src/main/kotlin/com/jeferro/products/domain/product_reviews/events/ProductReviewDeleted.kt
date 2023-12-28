package com.jeferro.products.domain.product_reviews.events

import com.jeferro.products.domain.product_reviews.entities.ProductReview
import com.jeferro.products.domain.product_reviews.entities.ProductReviewId
import com.jeferro.products.domain.shared.entities.auth.UserId
import com.jeferro.products.domain.shared.events.DomainEvent
import java.time.Instant

class ProductReviewDeleted(
    val productReviewId: ProductReviewId,
    occurredBy: UserId,
    occurredOn: Instant
) : DomainEvent(occurredBy, occurredOn) {

    companion object {
        fun productReviewDeletedOf(productReview: ProductReview): ProductReviewDeleted {
            return ProductReviewDeleted(
                productReview.id,
                productReview.updatedBy,
                productReview.updatedAt
            )
        }
    }
}
