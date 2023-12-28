package com.jeferro.products.infrastructure.product_reviews.adapters.mongo

import com.jeferro.products.components.products.mongo.poduct_reviews.ProductReviewsMongoDao
import com.jeferro.products.domain.product_reviews.entities.ProductReview
import com.jeferro.products.domain.product_reviews.entities.ProductReviewId
import com.jeferro.products.domain.product_reviews.repositories.ProductReviewsRepository
import com.jeferro.products.infrastructure.product_reviews.adapters.mongo.mappers.ProductReviewIdMongoMapper
import com.jeferro.products.infrastructure.product_reviews.adapters.mongo.mappers.ProductReviewMongoMapper
import org.springframework.stereotype.Repository

@Repository
class ProductReviewsMongoRepository(
    private val productReviewsMongoDao: ProductReviewsMongoDao
) : ProductReviewsRepository {

    private val productReviewIdMongoMapper = ProductReviewIdMongoMapper.instance

    private val productReviewMongoMapper = ProductReviewMongoMapper.instance

    override suspend fun save(productReview: ProductReview) {
        val productReviewDTO = productReviewMongoMapper.toDTO(productReview)

        productReviewsMongoDao.save(productReviewDTO)
    }

    override suspend fun findById(productReviewId: ProductReviewId): ProductReview? {
        val productReviewIdDTO = productReviewIdMongoMapper.toDTO(productReviewId)

        val productReviewDTO = productReviewsMongoDao.findById(productReviewIdDTO)
            ?: return null

        return productReviewMongoMapper.toEntity(productReviewDTO)
    }

    override suspend fun deleteById(productReviewId: ProductReviewId) {
        val productReviewIdDTO = productReviewIdMongoMapper.toDTO(productReviewId)

        productReviewsMongoDao.deleteById(productReviewIdDTO)
    }
}