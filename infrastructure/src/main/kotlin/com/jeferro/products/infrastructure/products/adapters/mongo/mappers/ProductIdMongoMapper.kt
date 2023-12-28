package com.jeferro.products.infrastructure.products.adapters.mongo.mappers

import com.jeferro.products.domain.products.entities.ProductId
import com.jeferro.products.infrastructure.shared.utils.mappers.SimpleIdentifierMapper
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
abstract class ProductIdMongoMapper : SimpleIdentifierMapper<ProductId, String>() {

    companion object {
        val instance = Mappers.getMapper(ProductIdMongoMapper::class.java)
    }

}