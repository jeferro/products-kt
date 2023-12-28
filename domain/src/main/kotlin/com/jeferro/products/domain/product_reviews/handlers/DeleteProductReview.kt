package com.jeferro.products.domain.product_reviews.handlers

import com.jeferro.products.domain.Roles.USER
import com.jeferro.products.domain.product_reviews.entities.ProductReview
import com.jeferro.products.domain.product_reviews.handlers.params.DeleteProductReviewParams
import com.jeferro.products.domain.product_reviews.repositories.ProductReviewsRepository
import com.jeferro.products.domain.product_reviews.services.ProductReviewFinder
import com.jeferro.products.domain.shared.events.bus.EventBus
import com.jeferro.products.domain.shared.handlers.CommandHandler
import com.jeferro.products.domain.shared.handlers.Handler

@Handler
class DeleteProductReview(
    private val productReviewFinder: ProductReviewFinder,
    private val productReviewsRepository: ProductReviewsRepository,
    private val eventBus: EventBus
) : CommandHandler<DeleteProductReviewParams, ProductReview>() {

    override val mandatoryRoles: List<String>
        get() = arrayListOf(USER)

    override suspend fun handle(params: DeleteProductReviewParams): ProductReview {
        val productReviewId = params.productReviewId

        val productReview = productReviewFinder.findById(productReviewId)

        productReview.delete(
            params.userId
        )

        productReviewsRepository.deleteById(productReviewId)

        eventBus.publishOf(productReview)

        return productReview
    }

}