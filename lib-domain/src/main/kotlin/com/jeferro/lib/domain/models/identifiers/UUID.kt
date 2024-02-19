package com.jeferro.lib.domain.models.identifiers

import java.util.UUID.randomUUID

class UUID(
    value: String
) : SimpleIdentifier<String>(value) {

    companion object {

        fun create(): UUID {
            val value = randomUUID().toString()
            return UUID(value)
        }
    }
}
