package com.jeferro.lib.domain.models.values

import java.util.stream.Stream

open class ValueCollection<V : Value>(
    protected val values: List<V>
) : Value() {

    val isEmpty
        get () = values.isEmpty()

    val isNotEmpty
        get () = values.isNotEmpty()

    val size
        get () = values.size

    operator fun get(index: Int): V {
        return values[index]
    }

    fun contains(value: V): Boolean {
        return values.contains(value)
    }

    fun filter(predicate: (V) -> Boolean): Stream<V> {
        return values.filter(predicate).stream()
    }

    fun <R> map(transform: (V) -> R): Stream<R> {
        return values.map(transform).stream()
    }

    fun forEach(action: (V) -> Unit) {
        values.forEach(action)
    }
}