package com.jeferro.products.domain.shared.entities.identifiers

import com.jeferro.products.domain.shared.exceptions.ValueValidationException.Companion.valueValidationExceptionOf

abstract class SimpleIdentifier<T>(
    val value: T
) : Identifier() {

    init {
        if (value is String && value.isBlank()) {
            throw valueValidationExceptionOf("Identifier can't be blank")
        }
    }

    final override fun toString(): String {
        return value.toString()
    }

}
