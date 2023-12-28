package com.jeferro.products.domain.product_reviews.entities

import com.jeferro.products.domain.products.entities.ProductId
import com.jeferro.products.domain.shared.entities.auth.UserId
import com.jeferro.products.domain.shared.entities.identifiers.Identifier

class ProductReviewId(
    val productId: ProductId,
    val userId: UserId
) : Identifier() {

    fun belongsToUser(userId: UserId): Boolean {
        return this.userId == userId
    }
}