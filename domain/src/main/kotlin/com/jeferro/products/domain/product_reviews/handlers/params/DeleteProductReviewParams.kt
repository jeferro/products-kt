package com.jeferro.products.domain.product_reviews.handlers.params

import com.jeferro.products.domain.product_reviews.entities.ProductReview
import com.jeferro.products.domain.product_reviews.entities.ProductReviewId
import com.jeferro.products.domain.shared.entities.auth.Auth
import com.jeferro.products.domain.shared.handlers.params.Params

class DeleteProductReviewParams(
    auth: Auth,
    val productReviewId: ProductReviewId
) : Params<ProductReview>(auth)