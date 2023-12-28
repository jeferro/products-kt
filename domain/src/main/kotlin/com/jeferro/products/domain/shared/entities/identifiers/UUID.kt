package com.jeferro.products.domain.shared.entities.identifiers

import java.util.UUID.randomUUID

class UUID(
    value: String
) : SimpleIdentifier<String>(value) {

    companion object {

        fun uuid(): UUID {
            val value = randomUUID().toString()
            return UUID(value)
        }
    }
}
