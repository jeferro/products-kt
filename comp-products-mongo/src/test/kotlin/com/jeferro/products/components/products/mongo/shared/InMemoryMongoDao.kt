package com.jeferro.products.components.products.mongo.shared

import com.jeferro.products.components.products.mongo.shared.dtos.MongoDocument
import kotlinx.coroutines.flow.*
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

open class InMemoryMongoDao<D : MongoDocument> : CoroutineCrudRepository<D, String> {

    protected val data = mutableMapOf<String, D>()

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

    override suspend fun count(): Long {
        return data.size.toLong()
    }

    override suspend fun delete(entity: D) {
        data.remove(entity.id)
    }

    override suspend fun deleteAll() {
        data.clear()
    }

    override suspend fun <S : D> deleteAll(entityStream: Flow<S>) {
        entityStream.map { data.remove(it.id) }
    }

    override suspend fun deleteAll(entities: Iterable<D>) {
        entities.forEach { data.remove(it.id) }
    }

    override suspend fun deleteAllById(ids: Iterable<String>) {
        ids.forEach { data.remove(it) }
    }

    override suspend fun deleteById(id: String) {
        data.remove(id)
    }

    override suspend fun existsById(id: String): Boolean {
        return data.containsKey(id)
    }

    override fun findAll(): Flow<D> {
        return data.values.asFlow()
    }

    override fun findAllById(ids: Iterable<String>): Flow<D> {
        return data.filter { entry -> ids.contains(entry.key) }
            .values
            .asFlow()

    }

    override fun findAllById(ids: Flow<String>): Flow<D> {
        return ids.mapNotNull { id -> data[id] }
    }

    override suspend fun findById(id: String): D? {
        return data[id]
    }

    override suspend fun <S : D> save(entity: S): D {
        data[entity.id] = entity

        return entity
    }

    override fun <S : D> saveAll(entities: Iterable<S>): Flow<S> {
        entities.forEach { entity -> data[entity.id] = entity }

        return entities.asFlow()
    }

    override fun <S : D> saveAll(entityStream: Flow<S>): Flow<S> {
        entityStream.onEach { entity -> data[entity.id] = entity }

        return entityStream
    }
}