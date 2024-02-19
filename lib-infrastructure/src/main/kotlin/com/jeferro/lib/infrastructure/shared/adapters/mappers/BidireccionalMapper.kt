package com.jeferro.lib.infrastructure.shared.adapters.mappers

interface BidireccionalMapper<E, D> : ToEntityMapper<E, D>, ToDTOMapper<E, D> {
}