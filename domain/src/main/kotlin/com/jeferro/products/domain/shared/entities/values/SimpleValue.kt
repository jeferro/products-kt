package com.jeferro.products.domain.shared.entities.values

import com.jeferro.products.domain.shared.exceptions.ValueValidationException

abstract class SimpleValue<T>(
    val value: T
) : Value() {

    init {
        if (value is String && value.isBlank()) {
            throw ValueValidationException.valueValidationExceptionOf("Value can't be blank")
        }
    }

    final override fun toString(): String {
        return value.toString()
    }
}