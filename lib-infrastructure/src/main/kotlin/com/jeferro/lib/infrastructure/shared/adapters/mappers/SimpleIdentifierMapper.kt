package com.jeferro.lib.infrastructure.shared.adapters.mappers

import com.jeferro.lib.domain.models.identifiers.SimpleIdentifier
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
