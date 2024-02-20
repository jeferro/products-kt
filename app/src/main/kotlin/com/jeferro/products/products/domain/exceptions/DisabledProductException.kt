package com.jeferro.products.products.domain.exceptions

import com.jeferro.lib.domain.exceptions.ConflictException
import com.jeferro.products.products.domain.models.ProductId

class DisabledProductException(
    message: String
): ConflictException(message, null) {

    companion object {
        fun create(productId: ProductId): DisabledProductException {
            return DisabledProductException("Forbidden operation because product '$productId' is disabled")
        }
    }
}