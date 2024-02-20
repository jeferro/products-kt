package com.jeferro.products.product_reviews.infrastructure.adapters.mongo

import com.jeferro.products.product_reviews.domain.models.ProductReview
import com.jeferro.products.product_reviews.domain.models.ProductReviewIdMother.oneProductReviewId
import com.jeferro.products.product_reviews.domain.models.ProductReviewMother.oneProductReview
import com.jeferro.products.product_reviews.infrastructure.adapters.mongo.daos.ProductReviewsMongoDao
import com.jeferro.products.product_reviews.infrastructure.adapters.mongo.mappers.ProductReviewMongoMapper
import com.jeferro.products.shared.infrastructure.adapters.mongo.MongodbContainerCreator
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Import
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@DataMongoTest
@Import(value = [ProductReviewsRepositoryMongo::class])
class ProductReviewsRepositoryMongoIT {

    companion object {

        @Container
        @ServiceConnection
        val mongodbContainer = MongodbContainerCreator.create()
    }

    @Autowired
    private lateinit var productReviewsMongoDao: ProductReviewsMongoDao

    @Autowired
    private lateinit var productReviewsRepositoryMongo: ProductReviewsRepositoryMongo

    @Nested
    inner class SaveTests {

        @Test
        fun `should save new product review`() = runTest {
            initializeDatabaseWith()

            val newProductReview = oneProductReview()
            productReviewsRepositoryMongo.save(newProductReview)

            val foundProductReview = productReviewsRepositoryMongo.findById(newProductReview.id)
            assertEquals(newProductReview, foundProductReview)
        }

        @Test
        fun `should update product review`() = runTest {
            val oneProductReview = oneProductReview()
            initializeDatabaseWith(oneProductReview)

            val comment = "new comment"
            oneProductReview.update(comment, oneProductReview.createdBy)
            oneProductReview.pullEvents()

            productReviewsRepositoryMongo.save(oneProductReview)

            val foundProductReview = productReviewsRepositoryMongo.findById(oneProductReview.id)
            assertEquals(oneProductReview, foundProductReview)
        }
    }

    @Nested
    inner class FindByIdTests {

        @Test
        fun `should return data when product review exists`() = runTest {
            val expected = oneProductReview()
            initializeDatabaseWith(expected)

            val result = productReviewsRepositoryMongo.findById(expected.id)

            assertEquals(expected, result)
        }

        @Test
        fun `should return null when product review doesn't exist`() = runTest {
            initializeDatabaseWith()

            val productReviewId = oneProductReviewId()
            val result = productReviewsRepositoryMongo.findById(productReviewId)

            assertNull(result)
        }
    }

    @Nested
    inner class DeleteByIdTests {

        @Test
        fun `should delete product review when it exists`() = runTest {
            val expected = oneProductReview()
            initializeDatabaseWith(expected)

            productReviewsRepositoryMongo.deleteById(expected.id)

            val foundProductReview = productReviewsRepositoryMongo.findById(expected.id)
            assertNull(foundProductReview)
        }

        @Test
        fun `should do nothing when product doesn't exist`() = runTest {
            initializeDatabaseWith()

            val productReviewId = oneProductReviewId()
            productReviewsRepositoryMongo.deleteById(productReviewId)

            val foundProductReview = productReviewsRepositoryMongo.findById(productReviewId)
            assertNull(foundProductReview)
        }
    }

    private suspend fun initializeDatabaseWith(vararg productReviews: ProductReview) {
        productReviewsMongoDao.deleteAll()

        val dtos = productReviews.toList()
        ProductReviewMongoMapper.instance.toDTOList(dtos)
            .forEach { productReviewDTO -> productReviewsMongoDao.save(productReviewDTO) }
    }
}