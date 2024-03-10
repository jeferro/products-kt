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
            authId: UserId
        ): Product {
            val metadata = Metadata.create(authId)

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
        authId: UserId
    ) {
        if (isDisabled) {
            throw DisabledProductException.create(id)
        }

        this.title = title
        this.description = description

        markAsModifyBy(authId)

        recordEvent(
            ProductUpserted.create(this)
        )
    }

    fun changeActivation(enabled: Boolean, authId: UserId) {
        this.isEnabled = enabled

        markAsModifyBy(authId)

        recordEvent(
            create(this)
        )
    }

    fun delete(authId: UserId) {
        markAsModifyBy(authId)

        recordEvent(
            ProductDeleted.create(this)
        )
    }
}
