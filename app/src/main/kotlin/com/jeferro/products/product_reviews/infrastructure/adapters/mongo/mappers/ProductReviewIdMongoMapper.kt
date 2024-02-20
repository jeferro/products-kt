package com.jeferro.products.product_reviews.infrastructure.adapters.mongo.mappers

import com.jeferro.lib.infrastructure.shared.adapters.mappers.BidireccionalMapper
import com.jeferro.products.accounts.infrastructure.adapters.mongo.mappers.UserIdMongoMapper
import com.jeferro.products.product_reviews.domain.models.ProductReviewId
import com.jeferro.products.products.infrastructure.adapters.mongo.mappers.ProductIdMongoMapper
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
abstract class ProductReviewIdMongoMapper : BidireccionalMapper<ProductReviewId, String> {

    companion object {
        val instance = Mappers.getMapper(ProductReviewIdMongoMapper::class.java)
    }

    private val userIdMongoMapper = UserIdMongoMapper.instance

    private val productIdMongoMapper = ProductIdMongoMapper.instance

    override fun toDTO(entity: ProductReviewId): String {
        return "${entity.productId}:${entity.userId}"
    }

    override fun toEntity(dto: String): ProductReviewId {
        val split = dto.split(":")

        if (split.size != 2) {
            throw IllegalArgumentException("Product review id '$dto' has a incorrect format")
        }

        val productId = productIdMongoMapper.toEntity(split[0])
        val userId = userIdMongoMapper.toEntity(split[1])

        return ProductReviewId(
            productId,
            userId
        )
    }

}