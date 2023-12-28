package com.jeferro.products.infrastructure.product_reviews.adapters.mongo.mappers

import com.jeferro.products.domain.product_reviews.entities.ProductReviewId
import com.jeferro.products.infrastructure.products.adapters.mongo.mappers.ProductIdMongoMapper
import com.jeferro.products.infrastructure.shared.components.mongo.mappers.UserIdMongoMapper
import com.jeferro.products.infrastructure.shared.utils.mappers.BidireccionalMapper
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