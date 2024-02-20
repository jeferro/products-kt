package com.jeferro.products.product_reviews.application

import com.jeferro.lib.domain.exceptions.ForbiddenException
import com.jeferro.lib.domain.exceptions.UnauthorizedException
import com.jeferro.products.product_reviews.domain.events.ProductReviewDeleted
import com.jeferro.products.product_reviews.domain.exceptions.ProductReviewNotFoundException
import com.jeferro.products.product_reviews.application.operations.DeleteProductReview
import com.jeferro.products.product_reviews.domain.models.ProductReviewIdMother.oneProductReviewId
import com.jeferro.products.product_reviews.domain.models.ProductReviewMother.oneProductReview
import com.jeferro.products.product_reviews.domain.repositories.ProductReviewsRepositoryInMemory
import com.jeferro.products.product_reviews.domain.services.ProductReviewFinder
import com.jeferro.products.shared.domain.events.EventBusInMemory
import com.jeferro.products.shared.domain.models.auth.AuthMother.oneAnonymousAuth
import com.jeferro.products.shared.domain.models.auth.AuthMother.oneUserAuth
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class DeleteProductReviewTest {

    private val productReviewsRepositoryInMemory = ProductReviewsRepositoryInMemory()

    private val productReviewFinder = ProductReviewFinder(
        productReviewsRepositoryInMemory
    )

    private val eventBusInMemory = EventBusInMemory()

    private val deleteProductReviewHandler = DeleteProductReviewHandler(
        productReviewFinder,
        productReviewsRepositoryInMemory,
        eventBusInMemory
    )

    @Test
    fun `should delete product review when it exists`() = runTest {
        val oneProductReview = oneProductReview()
        productReviewsRepositoryInMemory.reset(oneProductReview)

        val userAuth = oneUserAuth()
        val params = DeleteProductReview(
            userAuth,
            oneProductReview.id
        )

        val result = deleteProductReviewHandler.execute(params)

        assertAll(
            "check handler result",
            { assertEquals(oneProductReview, result) }
        )

        assertAll(
            "check repository",
            { assertTrue(productReviewsRepositoryInMemory.isEmpty) }
        )

        val expectedEvent = ProductReviewDeleted.create(oneProductReview)

        assertAll(
            "check product created event was published",
            { assertTrue(eventBusInMemory.containsNumElements(1)) },
            { assertEquals(expectedEvent, eventBusInMemory.first) }
        )
    }

    @Test
    fun `should fail when it doesn't exist`() = runTest {
        productReviewsRepositoryInMemory.reset()

        val userAuth = oneUserAuth()
        val oneProductReviewId = oneProductReviewId()
        val params = DeleteProductReview(
            userAuth,
            oneProductReviewId
        )

        assertThrows<ProductReviewNotFoundException> {
            deleteProductReviewHandler.execute(params)
        }
    }

    @Test
    fun `should fail when anonymous user executes the handler`() = runTest {
        productReviewsRepositoryInMemory.reset()

        val userAuth = oneAnonymousAuth()
        val oneProductReviewId = oneProductReviewId()
        val params = DeleteProductReview(
            userAuth,
            oneProductReviewId
        )

        assertThrows<UnauthorizedException> {
            deleteProductReviewHandler.execute(params)
        }
    }

    @Test
    fun `should fail when user doesn't have mandatory permissions`() = runTest {
        productReviewsRepositoryInMemory.reset()

        val userRoles = emptyList<String>()
        val userAuth = oneUserAuth(userRoles)
        val oneProductReviewId = oneProductReviewId()
        val params = DeleteProductReview(
            userAuth,
            oneProductReviewId
        )

        assertThrows<ForbiddenException> {
            deleteProductReviewHandler.execute(params)
        }
    }
}