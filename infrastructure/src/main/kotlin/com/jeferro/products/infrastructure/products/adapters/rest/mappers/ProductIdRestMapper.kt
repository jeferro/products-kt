package com.jeferro.products.infrastructure.products.adapters.rest.mappers

import com.jeferro.products.domain.products.entities.ProductId
import com.jeferro.products.infrastructure.shared.utils.mappers.SimpleIdentifierMapper
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
abstract class ProductIdRestMapper : SimpleIdentifierMapper<ProductId, String>() {

    companion object {
        val instance = Mappers.getMapper(ProductIdRestMapper::class.java)
    }
}