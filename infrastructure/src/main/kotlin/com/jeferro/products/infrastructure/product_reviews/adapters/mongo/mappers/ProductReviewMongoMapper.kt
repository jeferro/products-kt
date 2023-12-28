package com.jeferro.products.infrastructure.product_reviews.adapters.mongo.mappers

import com.jeferro.products.components.products.mongo.poduct_reviews.dtos.ProductReviewMongoDTO
import com.jeferro.products.domain.product_reviews.entities.ProductReview
import com.jeferro.products.infrastructure.shared.components.mongo.mappers.MetadataMongoMapper
import com.jeferro.products.infrastructure.shared.utils.mappers.BidireccionalMapper
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper(
    uses = [
        ProductReviewIdMongoMapper::class,
        MetadataMongoMapper::class
    ]
)
abstract class ProductReviewMongoMapper : BidireccionalMapper<ProductReview, ProductReviewMongoDTO> {

    companion object {
        val instance = Mappers.getMapper(ProductReviewMongoMapper::class.java)
    }
}