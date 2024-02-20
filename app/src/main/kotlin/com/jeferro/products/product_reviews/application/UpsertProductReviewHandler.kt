package com.jeferro.products.product_reviews.application

import com.jeferro.lib.domain.events.bus.EventBus
import com.jeferro.lib.application.CommandHandler
import com.jeferro.lib.application.Handler
import com.jeferro.products.product_reviews.application.operations.UpsertProductReview
import com.jeferro.products.product_reviews.domain.models.ProductReview
import com.jeferro.products.product_reviews.domain.repositories.ProductReviewsRepository
import com.jeferro.products.shared.Roles.USER

@Handler
class UpsertProductReviewHandler(
    private val productReviewsRepository: ProductReviewsRepository,
    private val eventBus: EventBus
) : CommandHandler<UpsertProductReview, ProductReview>() {

    override val mandatoryRoles: List<String>
        get() = arrayListOf(USER)

    override suspend fun handle(operation: UpsertProductReview): ProductReview {
        val productReviewId = operation.productReviewId

        var productReview = productReviewsRepository.findById(productReviewId)

        if (productReview == null) {
            productReview = ProductReview.create(
                productReviewId,
                operation.comment,
                operation.userId
            )
        } else {
            productReview.update(
                operation.comment,
                operation.userId
            )
        }

        productReviewsRepository.save(productReview)

        eventBus.publishAll(productReview)

        return productReview
    }

}