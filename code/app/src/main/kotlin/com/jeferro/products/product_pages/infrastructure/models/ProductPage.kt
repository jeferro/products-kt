package com.jeferro.products.product_pages.infrastructure.models

import com.jeferro.lib.domain.models.auth.UserId
import com.jeferro.lib.domain.models.entities.Projection
import com.jeferro.lib.domain.models.metadata.Metadata
import com.jeferro.products.product_reviews.domain.models.ProductReview
import com.jeferro.products.products.domain.models.Product
import com.jeferro.products.products.domain.models.ProductId

class ProductPage(
    productId: ProductId,
    private var product: Product?,
    private var reviews: List<ProductReview>,
    metadata: Metadata
) : Projection<ProductId>(productId, metadata) {

    companion object {

        fun createFromProduct(
            product: Product,
            authId: UserId
        ): ProductPage {
            val metadata = Metadata.create(authId)

            return ProductPage(
                product.id,
                product,
                emptyList(),
                metadata
            );
        }

        fun createFromProductReview(
            productReview: ProductReview,
            authId: UserId
        ): ProductPage {
            val metadata = Metadata.create(authId)

            return ProductPage(
                productReview.productId,
                null,
                emptyList(),
                metadata
            );
        }
    }

    val isDraft = (product == null)

    fun updateProduct(
        product: Product,
        authId: UserId
    ) {
        if (product.updatedAt <= this.product?.updatedAt) {
            return
        }

        this.product = product
    }
}