package com.jeferro.products.infrastructure.shared.utils.mappers

interface ToEntityMapper<E, D> {

    fun toEntity(dto: D): E

    fun toEntityList(dtos: List<D>): List<E>

    fun toEntitySet(dtos: Set<D>): Set<E>
}