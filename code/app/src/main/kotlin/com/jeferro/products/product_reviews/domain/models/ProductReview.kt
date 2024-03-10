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
            authId: UserId
        ) {
            if (!productReviewId.belongsToUser(authId)) {
                throw ForbiddenException.createOf(authId)
            }
        }

        fun create(
            productReviewId: ProductReviewId,
            comment: String,
            authId: UserId
        ): ProductReview {
            ensureBelongsToUser(productReviewId, authId)

            val metadata = Metadata.create(authId)

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

    val productId = id.productId

    fun update(
        comment: String,
        authId: UserId
    ) {
        ensureBelongsToUser(id, authId)

        this.comment = comment

        markAsModifyBy(authId)

        recordEvent(
            ProductReviewUpserted.create(this)
        )
    }

    fun delete(authId: UserId) {
        ensureBelongsToUser(id, authId)

        markAsModifyBy(authId)

        recordEvent(
            ProductReviewDeleted.create(this)
        )
    }
}
