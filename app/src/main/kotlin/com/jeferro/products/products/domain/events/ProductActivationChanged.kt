package com.jeferro.products.products.domain.events

import com.jeferro.lib.domain.events.DomainEvent
import com.jeferro.lib.domain.models.auth.UserId
import com.jeferro.products.products.domain.models.ProductId
import com.jeferro.products.products.domain.models.Product
import java.time.Instant

class ProductActivationChanged(
    val productId: ProductId,
    occurredBy: UserId,
    occurredOn: Instant
) : DomainEvent(occurredBy, occurredOn) {

    companion object {
        fun create(product: Product): ProductActivationChanged {
            return ProductActivationChanged(
                product.id,
                product.updatedBy,
                product.updatedAt
            )
        }
    }
}
