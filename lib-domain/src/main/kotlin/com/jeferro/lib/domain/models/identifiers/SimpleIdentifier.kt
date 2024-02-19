package com.jeferro.lib.domain.models.identifiers

import com.jeferro.lib.domain.exceptions.ValueValidationException

abstract class SimpleIdentifier<T>(
    val value: T
) : Identifier() {

    init {
        if (value is String && value.isBlank()) {
            throw ValueValidationException.create("Identifier can't be blank")
        }
    }

    final override fun toString(): String {
        return value.toString()
    }

}
