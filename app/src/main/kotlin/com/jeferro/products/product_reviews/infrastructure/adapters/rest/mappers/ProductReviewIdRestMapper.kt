package com.jeferro.products.product_reviews.infrastructure.adapters.rest.mappers

import com.jeferro.lib.infrastructure.shared.adapters.mappers.BidireccionalMapper
import com.jeferro.products.accounts.infrastructure.adapters.rest.mappers.UserIdRestMapper
import com.jeferro.products.components.products.rest.dtos.ProductReviewIdRestDTO
import com.jeferro.products.product_reviews.domain.models.ProductReviewId
import com.jeferro.products.products.infrastructure.adapters.rest.mappers.ProductIdRestMapper
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

@Mapper(
    uses = [
        UserIdRestMapper::class,
        ProductIdRestMapper::class
    ]
)
abstract class ProductReviewIdRestMapper : BidireccionalMapper<ProductReviewId, ProductReviewIdRestDTO> {

    companion object {
        val instance = Mappers.getMapper(ProductReviewIdRestMapper::class.java)
    }

    @Mapping(source = "productIdDto", target = "productId")
    @Mapping(source = "userIdDto", target = "userId")
    abstract fun toEntity(productIdDto: String, userIdDto: String): ProductReviewId
}