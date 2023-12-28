package com.jeferro.products.infrastructure.products.adapters.mongo.mappers

import com.jeferro.products.components.products.mongo.products.dtos.ProductMongoDTO
import com.jeferro.products.domain.products.entities.Product
import com.jeferro.products.domain.products.entities.Products
import com.jeferro.products.infrastructure.shared.components.mongo.mappers.MetadataMongoMapper
import com.jeferro.products.infrastructure.shared.utils.mappers.BidireccionalMapper
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