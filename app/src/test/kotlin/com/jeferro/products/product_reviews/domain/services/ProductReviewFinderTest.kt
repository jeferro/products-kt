package com.jeferro.products.product_reviews.domain.services

import com.jeferro.products.product_reviews.domain.exceptions.ProductReviewNotFoundException
import com.jeferro.products.product_reviews.domain.models.ProductReviewIdMother.oneProductReviewId
import com.jeferro.products.product_reviews.domain.models.ProductReviewMother.oneProductReview
import com.jeferro.products.product_reviews.domain.repositories.ProductReviewsRepositoryInMemory
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProductReviewFinderTest {

    private val productReviewsRepository = ProductReviewsRepositoryInMemory()

    private val productFinder = ProductReviewFinder(
        productReviewsRepository
    )

    @Test
    fun `should return product review when it exists`() = runTest {
        val expectedProductReview = oneProductReview()

        productReviewsRepository.reset(expectedProductReview)

        val result = productFinder.findById(expectedProductReview.id)

        assertEquals(expectedProductReview, result)
    }

    @Test
    fun `should fail when product doesn't exist`() = runTest {
        productReviewsRepository.reset()

        val productReviewId = oneProductReviewId()

        assertThrows<ProductReviewNotFoundException> {
            productFinder.findById(productReviewId)
        }
    }
}