package com.jeferro.products.product_reviews.application.operations

import com.jeferro.lib.application.operations.Operation
import com.jeferro.lib.domain.models.auth.Auth
import com.jeferro.products.product_reviews.domain.models.ProductReview
import com.jeferro.products.product_reviews.domain.models.ProductReviewId

class UpsertProductReview(
    auth: Auth,
    val productReviewId: ProductReviewId,
    val comment: String
) : Operation<ProductReview>(auth)