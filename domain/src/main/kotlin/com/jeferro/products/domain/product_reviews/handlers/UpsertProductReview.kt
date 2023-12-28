package com.jeferro.products.domain.product_reviews.handlers

import com.jeferro.products.domain.Roles.USER
import com.jeferro.products.domain.product_reviews.entities.ProductReview
import com.jeferro.products.domain.product_reviews.entities.ProductReview.Companion.productReviewOf
import com.jeferro.products.domain.product_reviews.handlers.params.UpsertProductReviewParams
import com.jeferro.products.domain.product_reviews.repositories.ProductReviewsRepository
import com.jeferro.products.domain.shared.events.bus.EventBus
import com.jeferro.products.domain.shared.handlers.CommandHandler
import com.jeferro.products.domain.shared.handlers.Handler

@Handler
class UpsertProductReview(
    private val productReviewsRepository: ProductReviewsRepository,
    private val eventBus: EventBus
) : CommandHandler<UpsertProductReviewParams, ProductReview>() {

    override val mandatoryRoles: List<String>
        get() = arrayListOf(USER)

    override suspend fun handle(params: UpsertProductReviewParams): ProductReview {
        val productReviewId = params.productReviewId

        var productReview = productReviewsRepository.findById(productReviewId)

        if (productReview == null) {
            productReview = productReviewOf(
                productReviewId,
                params.comment,
                params.userId
            )
        } else {
            productReview.update(
                params.comment,
                params.userId
            )
        }

        productReviewsRepository.save(productReview)

        eventBus.publishOf(productReview)

        return productReview
    }

}