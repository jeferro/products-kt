package com.jeferro.products.infrastructure.shared.utils.mappers

interface BidireccionalMapper<E, D> : ToEntityMapper<E, D>, ToDTOMapper<E, D> {
}