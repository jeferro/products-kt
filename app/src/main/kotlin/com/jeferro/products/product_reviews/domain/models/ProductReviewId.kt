package com.jeferro.products.product_reviews.domain.models

import com.jeferro.lib.domain.models.auth.UserId
import com.jeferro.lib.domain.models.identifiers.Identifier
import com.jeferro.products.products.domain.models.ProductId

class ProductReviewId(
    val productId: ProductId,
    val userId: UserId
) : Identifier() {

    fun belongsToUser(userId: UserId): Boolean {
        return this.userId == userId
    }
}