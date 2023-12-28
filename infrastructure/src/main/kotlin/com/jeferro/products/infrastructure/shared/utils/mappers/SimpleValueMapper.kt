package com.jeferro.products.infrastructure.shared.utils.mappers

import com.jeferro.products.domain.shared.entities.values.SimpleValue
import org.mapstruct.Mapping
import java.io.Serializable

abstract class SimpleValueMapper<E : SimpleValue<D>, D : Serializable>
    : BidireccionalMapper<E, D> {

    override fun toDTO(entity: E): D {
        return entity.value;
    }

    @Mapping(source = "dto", target = "value")
    abstract override fun toEntity(dto: D): E
}
