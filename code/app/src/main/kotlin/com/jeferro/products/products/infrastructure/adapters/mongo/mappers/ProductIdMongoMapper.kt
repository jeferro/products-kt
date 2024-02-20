package com.jeferro.products.products.infrastructure.adapters.mongo.mappers

import com.jeferro.lib.infrastructure.shared.adapters.mappers.SimpleIdentifierMapper
import com.jeferro.products.products.domain.models.ProductId
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
abstract class ProductIdMongoMapper : SimpleIdentifierMapper<ProductId, String>() {

    companion object {
        val instance = Mappers.getMapper(ProductIdMongoMapper::class.java)
    }

}