package com.jeferro.lib.domain.models.entities

import com.jeferro.lib.domain.models.identifiers.Identifier
import com.jeferro.lib.domain.models.values.Value
import java.util.stream.Stream

open class EntityCollection<I: Identifier, E: Entity<I>>(
    protected val entities: List<E>
): Value() {

    val isEmpty
        get () = entities.isEmpty()

    val isNotEmpty
        get () = entities.isNotEmpty()

    val size
        get () = entities.size

    val ids
        get () = entities.map { entity -> entity.id }

    operator fun get(index: Int): E {
        return entities[index]
    }

    fun findById(id: I): E? {
        return entities.find { entity -> entity.id == id }
    }

    fun contains(entity: E): Boolean {
        return entities.contains(entity)
    }

    fun containsId(id: I): Boolean {
        return entities.find { entity -> entity.id == id } == null
    }

    fun filter(predicate: (E) -> Boolean): Stream<E> {
        return entities.filter(predicate).stream()
    }

    fun <R> map(transform: (E) -> R): Stream<R> {
        return entities.map(transform).stream()
    }

    fun forEach(action: (E) -> Unit) {
        entities.forEach(action)
    }
}