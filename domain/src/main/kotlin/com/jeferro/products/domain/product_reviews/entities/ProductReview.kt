package com.jeferro.products.domain.product_reviews.entities

import com.jeferro.products.domain.product_reviews.events.ProductReviewDeleted.Companion.productReviewDeletedOf
import com.jeferro.products.domain.product_reviews.events.ProductReviewUpserted.Companion.productReviewUpsertedOf
import com.jeferro.products.domain.shared.entities.auth.UserId
import com.jeferro.products.domain.shared.entities.entities.Aggregate
import com.jeferro.products.domain.shared.entities.metadata.Metadata
import com.jeferro.products.domain.shared.entities.metadata.Metadata.Companion.metadataOf
import com.jeferro.products.domain.shared.exceptions.ForbiddenException.Companion.forbiddenExceptionOf

class ProductReview(
    id: ProductReviewId,
    comment: String,
    metadata: Metadata
) : Aggregate<ProductReviewId>(id, metadata) {

    var comment = comment
        private set

    companion object {

        fun productReviewOf(
            id: ProductReviewId,
            comment: String,
            userId: UserId
        ): ProductReview {
            val metadata = metadataOf(id.userId)

            val productReview = ProductReview(
                id,
                comment,
                metadata
            )

            productReview.ensureBelongsToUser(userId)

            productReview.recordEvent(
                productReviewUpsertedOf(productReview)
            )

            return productReview
        }
    }

    fun update(
        comment: String,
        userId: UserId
    ) {
        ensureBelongsToUser(userId)

        this.comment = comment

        markAsModifyBy(userId)

        recordEvent(
            productReviewUpsertedOf(this)
        )
    }

    fun delete(userId: UserId) {
        ensureBelongsToUser(userId)

        markAsModifyBy(userId)

        recordEvent(
            productReviewDeletedOf(this)
        )
    }

    private fun ensureBelongsToUser(userId: UserId) {
        if (!id.belongsToUser(userId)) {
            throw forbiddenExceptionOf(userId)
        }
    }
}
