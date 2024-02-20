package com.jeferro.products.product_reviews.infrastructure.adapters.mongo

import com.jeferro.products.product_reviews.domain.models.ProductReview
import com.jeferro.products.product_reviews.domain.models.ProductReviewId
import com.jeferro.products.product_reviews.domain.repositories.ProductReviewsRepository
import com.jeferro.products.product_reviews.infrastructure.adapters.mongo.daos.ProductReviewsMongoDao
import com.jeferro.products.product_reviews.infrastructure.adapters.mongo.mappers.ProductReviewIdMongoMapper
import com.jeferro.products.product_reviews.infrastructure.adapters.mongo.mappers.ProductReviewMongoMapper
import org.springframework.stereotype.Repository

@Repository
class ProductReviewsRepositoryMongo(
    private val productReviewsMongoDao: ProductReviewsMongoDao
): ProductReviewsRepository {

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