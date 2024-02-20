package com.jeferro.products.product_reviews.application

import com.jeferro.lib.domain.events.bus.EventBus
import com.jeferro.lib.application.CommandHandler
import com.jeferro.lib.application.Handler
import com.jeferro.products.product_reviews.application.operations.DeleteProductReview
import com.jeferro.products.product_reviews.domain.models.ProductReview
import com.jeferro.products.product_reviews.domain.repositories.ProductReviewsRepository
import com.jeferro.products.product_reviews.domain.services.ProductReviewFinder
import com.jeferro.products.shared.Roles.USER

@Handler
class DeleteProductReviewHandler(
    private val productReviewFinder: ProductReviewFinder,
    private val productReviewsRepository: ProductReviewsRepository,
    private val eventBus: EventBus
) : CommandHandler<DeleteProductReview, ProductReview>() {

    override val mandatoryRoles: List<String>
        get() = arrayListOf(USER)

    override suspend fun handle(operation: DeleteProductReview): ProductReview {
        val productReviewId = operation.productReviewId

        val productReview = productReviewFinder.findById(productReviewId)

        productReview.delete(
            operation.userId
        )

        productReviewsRepository.deleteById(productReviewId)

        eventBus.publishAll(productReview)

        return productReview
    }

}