package com.jeferro.products.domain.products.exceptions

import com.jeferro.products.domain.products.entities.ProductId
import com.jeferro.products.domain.shared.exceptions.ConflictException

class DisabledProductException(
    message: String
): ConflictException(message, null) {

    companion object {
        fun disabledProductExceptionOf(productId: ProductId): DisabledProductException {
            return DisabledProductException("Forbidden operation because product '$productId' is disabled")
        }
    }
}