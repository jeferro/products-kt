package com.jeferro.products.products.infrastructure.adapters.mongo.mappers

import com.jeferro.lib.infrastructure.shared.adapters.mappers.BidireccionalMapper
import com.jeferro.products.domain.products.entities.Products
import com.jeferro.products.products.domain.models.Product
import com.jeferro.products.products.infrastructure.adapters.mongo.dtos.ProductMongoDTO
import com.jeferro.products.shared.infrastructure.adapters.mongo.mappers.MetadataMongoMapper
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper(
    uses = [
        ProductIdMongoMapper::class,
        MetadataMongoMapper::class
    ]
)
abstract class ProductMongoMapper : BidireccionalMapper<Product, ProductMongoDTO> {

    companion object {
        val instance = Mappers.getMapper(ProductMongoMapper::class.java)
    }

    abstract override fun toEntity(dto: ProductMongoDTO): Product

    abstract override fun toDTO(entity: Product): ProductMongoDTO

    fun toProducts(dtos: List<ProductMongoDTO>): Products {
        val entities = toEntityList(dtos)

        return Products.of(entities)
    }
}