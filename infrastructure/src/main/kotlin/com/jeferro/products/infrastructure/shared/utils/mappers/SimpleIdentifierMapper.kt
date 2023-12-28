package com.jeferro.products.infrastructure.shared.utils.mappers

import com.jeferro.products.domain.shared.entities.identifiers.SimpleIdentifier
import org.mapstruct.Mapping
import java.io.Serializable

abstract class SimpleIdentifierMapper<E : SimpleIdentifier<D>, D : Serializable>
    : BidireccionalMapper<E, D> {

    override fun toDTO(entity: E): D {
        return entity.value;
    }

    @Mapping(source = "dto", target = "value")
    abstract override fun toEntity(dto: D): E
}
