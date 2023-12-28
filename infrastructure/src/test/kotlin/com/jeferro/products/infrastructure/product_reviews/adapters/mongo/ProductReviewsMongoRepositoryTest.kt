package com.jeferro.products.infrastructure.product_reviews.adapters.mongo

import com.jeferro.products.components.products.mongo.product_reviews.dtos.ProductReviewMongoDTOMother.oneProductReviewMongoDTO
import com.jeferro.products.components.products.mongo.product_reviews.ProductReviewsInMemoryMongoDao
import com.jeferro.products.domain.product_reviews.entities.ProductReviewIdMother.oneProductReviewId
import com.jeferro.products.domain.product_reviews.entities.ProductReviewMother.oneProductReview
import com.jeferro.products.infrastructure.product_reviews.adapters.mongo.mappers.ProductReviewIdMongoMapper
import com.jeferro.products.infrastructure.product_reviews.adapters.mongo.mappers.ProductReviewMongoMapper
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ProductReviewsMongoRepositoryTest {

    private val productReviewIdMongoMapper = ProductReviewIdMongoMapper.instance

    private val productReviewMongoMapper = ProductReviewMongoMapper.instance

    private val productReviewsInMemoryMongoDao = ProductReviewsInMemoryMongoDao()

    private val productReviewsMongoRepository = ProductReviewsMongoRepository(
        productReviewsInMemoryMongoDao
    )

    @Nested
    inner class Save {

        @Test
        fun `should save product review when it doesn't exist`() = runTest {
            productReviewsInMemoryMongoDao.reset()

            val productReview = oneProductReview()
            productReviewsMongoRepository.save(productReview)

            val productReviewDTO = productReviewMongoMapper.toDTO(productReview)

            assertEquals(productReviewDTO, productReviewsInMemoryMongoDao.first)
        }

        @Test
        fun `should save product review when it exists`() = runTest {
            val productReviewDTO = oneProductReviewMongoDTO()
            productReviewsInMemoryMongoDao.reset(productReviewDTO)

            val productReview = productReviewMongoMapper.toEntity(productReviewDTO)

            productReviewsMongoRepository.save(productReview)

            assertEquals(productReviewDTO, productReviewsInMemoryMongoDao.first)
        }
    }

    @Nested
    inner class FindById {

        @Test
        fun `should return product review when it exists`() = runTest {
            val productReviewDTO = oneProductReviewMongoDTO()
            productReviewsInMemoryMongoDao.reset(productReviewDTO)

            val productReview = productReviewMongoMapper.toEntity(productReviewDTO)

            val result = productReviewsMongoRepository.findById(productReview.id)

            assertEquals(productReview, result)
        }

        @Test
        fun `should return null when it doesn't exist`() = runTest {
            productReviewsInMemoryMongoDao.reset()

            val productReviewId = oneProductReviewId()
            val result = productReviewsMongoRepository.findById(productReviewId)

            assertNull(result)
        }
    }

    @Nested
    inner class DeleteById {

        @Test
        fun `should delete product review when it exists`() = runTest {
            val productReviewDTO = oneProductReviewMongoDTO()
            productReviewsInMemoryMongoDao.reset(productReviewDTO)

            val productReviewId = productReviewIdMongoMapper.toEntity(productReviewDTO.id)

            productReviewsMongoRepository.deleteById(productReviewId)

            assertTrue(productReviewsInMemoryMongoDao.isEmpty)
        }

        @Test
        fun `should do nothing when it doesn't exist`() = runTest {
            productReviewsInMemoryMongoDao.reset()

            val productReviewId = oneProductReviewId()
            productReviewsMongoRepository.deleteById(productReviewId)

            assertTrue(productReviewsInMemoryMongoDao.isEmpty)
        }
    }

}