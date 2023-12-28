package com.jeferro.products.domain.product_reviews.handlers.params

import com.jeferro.products.domain.product_reviews.entities.ProductReview
import com.jeferro.products.domain.product_reviews.entities.ProductReviewId
import com.jeferro.products.domain.shared.handlers.params.Params
import com.jeferro.products.domain.shared.entities.auth.Auth

class UpsertProductReviewParams(
    auth: Auth,
    val productReviewId: ProductReviewId,
    val comment: String
) : Params<ProductReview>(auth)