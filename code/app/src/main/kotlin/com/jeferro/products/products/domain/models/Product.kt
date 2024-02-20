package com.jeferro.products.products.domain.models

import com.jeferro.lib.domain.models.auth.UserId
import com.jeferro.lib.domain.models.entities.Aggregate
import com.jeferro.lib.domain.models.metadata.Metadata
import com.jeferro.products.products.domain.events.ProductActivationChanged.Companion.create
import com.jeferro.products.products.domain.events.ProductDeleted
import com.jeferro.products.products.domain.events.ProductUpserted
import com.jeferro.products.products.domain.exceptions.DisabledProductException

class Product(
    id: ProductId,
    title: String,
    description: String,
    enabled: Boolean,
    metadata: Metadata
) : Aggregate<ProductId>(id, metadata) {

    var title = title
        private set

    var description = description
        private set

    var isEnabled = enabled
        private set

    val isDisabled
        get() = !isEnabled

    companion object {

        fun create(
            id: ProductId,
            title: String,
            description: String,
            userId: UserId
        ): Product {
            val metadata = Metadata.create(userId)

            val product = Product(
                id,
                title,
                description,
                true,
                metadata
            )

            product.recordEvent(
                ProductUpserted.create(product)
            )

            return product
        }
    }

    fun update(
        title: String,
        description: String,
        userId: UserId
    ) {
        if (isDisabled) {
            throw DisabledProductException.create(id)
        }

        this.title = title
        this.description = description

        markAsModifyBy(userId)

        recordEvent(
            ProductUpserted.create(this)
        )
    }

    fun changeActivation(enabled: Boolean, userId: UserId) {
        this.isEnabled = enabled

        markAsModifyBy(userId)

        recordEvent(
            create(this)
        )
    }

    fun delete(userId: UserId) {
        markAsModifyBy(userId)

        recordEvent(
            ProductDeleted.create(this)
        )
    }
}
