package com.jeferro.lib.domain.models.values

import com.jeferro.lib.domain.exceptions.ValueValidationException

abstract class SimpleValue<T>(
    val value: T
) : Value() {

    init {
        if (value is String && value.isBlank()) {
            throw ValueValidationException.create("Value can't be blank")
        }
    }

    final override fun toString(): String {
        return value.toString()
    }
}