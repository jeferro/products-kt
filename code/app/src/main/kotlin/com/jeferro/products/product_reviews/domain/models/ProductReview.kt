package com.jeferro.products.product_reviews.domain.models

import com.jeferro.lib.domain.exceptions.ForbiddenException
import com.jeferro.lib.domain.models.auth.UserId
import com.jeferro.lib.domain.models.entities.Aggregate
import com.jeferro.lib.domain.models.metadata.Metadata
import com.jeferro.products.product_reviews.domain.events.ProductReviewDeleted
import com.jeferro.products.product_reviews.domain.events.ProductReviewUpserted

class ProductReview(
    id: ProductReviewId,
    comment: String,
    metadata: Metadata
) : Aggregate<ProductReviewId>(id, metadata) {

    companion object {

        private fun ensureBelongsToUser(
            productReviewId: ProductReviewId,
            userId: UserId
        ) {
            if (!productReviewId.belongsToUser(userId)) {
                throw ForbiddenException.createOf(userId)
            }
        }

        fun create(
            productReviewId: ProductReviewId,
            comment: String,
            userId: UserId
        ): ProductReview {
            ensureBelongsToUser(productReviewId, userId)

            val metadata = Metadata.create(userId)

            val productReview = ProductReview(
                productReviewId,
                comment,
                metadata
            )

            productReview.recordEvent(
                ProductReviewUpserted.create(productReview)
            )

            return productReview
        }
    }

    var comment = comment
        private set

    fun update(
        comment: String,
        userId: UserId
    ) {
        ensureBelongsToUser(id, userId)

        this.comment = comment

        markAsModifyBy(userId)

        recordEvent(
            ProductReviewUpserted.create(this)
        )
    }

    fun delete(userId: UserId) {
        ensureBelongsToUser(id, userId)

        markAsModifyBy(userId)

        recordEvent(
            ProductReviewDeleted.create(this)
        )
    }
}
