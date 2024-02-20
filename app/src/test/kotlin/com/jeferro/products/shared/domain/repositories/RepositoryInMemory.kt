package com.jeferro.products.shared.domain.repositories

import com.jeferro.lib.domain.models.entities.Aggregate
import com.jeferro.lib.domain.models.identifiers.Identifier

abstract class RepositoryInMemory<K : Identifier, D : Aggregate<K>> {

    protected val data = mutableMapOf<K, D>()

    val isEmpty
        get() = data.isEmpty()

    val isNotEmpty
        get() = data.isNotEmpty()

    val first: D
        get() = data.values.first()

    fun clear() {
        data.clear()
    }

    fun reset(vararg data: D) {
        this.data.clear()

        data.forEach { item ->
            this.data[item.id] = item
        }
    }

    fun hasSize(num: Int): Boolean {
        return data.size == num
    }
}