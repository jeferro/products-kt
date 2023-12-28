package com.jeferro.products.domain.products.events

import com.jeferro.products.domain.products.entities.Product
import com.jeferro.products.domain.products.entities.ProductId
import com.jeferro.products.domain.shared.entities.auth.UserId
import com.jeferro.products.domain.shared.events.DomainEvent
import java.time.Instant

class ProductActivationChanged(
    val productId: ProductId,
    occurredBy: UserId,
    occurredOn: Instant
) : DomainEvent(occurredBy, occurredOn) {

    companion object {
        fun productActivationChangedOf(product: Product): ProductActivationChanged {
            return ProductActivationChanged(
                product.id,
                product.updatedBy,
                product.updatedAt
            )
        }
    }
}
