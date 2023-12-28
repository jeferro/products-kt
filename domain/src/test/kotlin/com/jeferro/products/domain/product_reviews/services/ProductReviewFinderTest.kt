package com.jeferro.products.domain.product_reviews.services

import com.jeferro.products.domain.product_reviews.entities.ProductReviewIdMother.oneProductReviewId
import com.jeferro.products.domain.product_reviews.entities.ProductReviewMother.oneProductReview
import com.jeferro.products.domain.product_reviews.exceptions.ProductReviewNotFoundException
import com.jeferro.products.domain.product_reviews.repositories.ProductReviewInMemoryRepository
import com.jeferro.products.domain.products.exceptions.ProductNotFoundException
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProductReviewFinderTest {

    private val productReviewsRepository = ProductReviewInMemoryRepository()

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