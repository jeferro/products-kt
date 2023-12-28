package com.jeferro.products.infrastructure.shared.utils.mappers


interface ToDTOMapper<E, D> {

    fun toDTO(entity: E): D

    fun toDTOList(entities: List<E>): List<D>

    fun toDTOSet(entities: Set<E>): Set<D>
}