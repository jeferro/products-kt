package com.jeferro.products.domain.products.entities

import com.jeferro.products.domain.products.events.ProductActivationChanged.Companion.productActivationChangedOf
import com.jeferro.products.domain.products.events.ProductDeleted.Companion.productDeletedOf
import com.jeferro.products.domain.products.events.ProductUpserted.Companion.productUpsertedOf
import com.jeferro.products.domain.products.exceptions.DisabledProductException.Companion.disabledProductExceptionOf
import com.jeferro.products.domain.shared.entities.auth.UserId
import com.jeferro.products.domain.shared.entities.entities.Aggregate
import com.jeferro.products.domain.shared.entities.metadata.Metadata
import com.jeferro.products.domain.shared.entities.metadata.Metadata.Companion.metadataOf

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

        fun productOf(
            id: ProductId,
            title: String,
            description: String,
            userId: UserId
        ): Product {
            val metadata = metadataOf(userId)

            val product = Product(
                id,
                title,
                description,
                true,
                metadata
            )

            product.recordEvent(
                productUpsertedOf(product)
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
            throw disabledProductExceptionOf(id)
        }

        this.title = title
        this.description = description

        markAsModifyBy(userId)

        recordEvent(
            productUpsertedOf(this)
        )
    }

    fun changeActivation(enabled: Boolean, userId: UserId) {
        this.isEnabled = enabled

        markAsModifyBy(userId)

        recordEvent(
            productActivationChangedOf(this)
        )
    }

    fun delete(userId: UserId) {
        markAsModifyBy(userId)

        recordEvent(
            productDeletedOf(this)
        )
    }
}
