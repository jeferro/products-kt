package com.jeferro.products.product_reviews.infrastructure.adapters.mongo.mappers

import com.jeferro.lib.infrastructure.shared.adapters.mappers.BidireccionalMapper
import com.jeferro.products.product_reviews.domain.models.ProductReview
import com.jeferro.products.product_reviews.infrastructure.adapters.mongo.dtos.ProductReviewMongoDTO
import com.jeferro.products.shared.infrastructure.adapters.mongo.mappers.MetadataMongoMapper
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