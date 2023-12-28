package com.jeferro.products.domain.product_reviews.handlers

import com.jeferro.products.domain.product_reviews.entities.ProductReviewIdMother.oneProductReviewId
import com.jeferro.products.domain.product_reviews.entities.ProductReviewMother.oneProductReview
import com.jeferro.products.domain.product_reviews.events.ProductReviewDeleted.Companion.productReviewDeletedOf
import com.jeferro.products.domain.product_reviews.exceptions.ProductReviewNotFoundException
import com.jeferro.products.domain.product_reviews.handlers.params.DeleteProductReviewParams
import com.jeferro.products.domain.product_reviews.repositories.ProductReviewInMemoryRepository
import com.jeferro.products.domain.product_reviews.services.ProductReviewFinder
import com.jeferro.products.domain.shared.entities.auth.AuthMother.oneAnonymousAuth
import com.jeferro.products.domain.shared.entities.auth.AuthMother.oneUserAuth
import com.jeferro.products.domain.shared.events.bus.EventBusInMemory
import com.jeferro.products.domain.shared.exceptions.ForbiddenException
import com.jeferro.products.domain.shared.exceptions.UnauthorizedException
import com.jeferro.products.domain.shared.services.logger.FakeLoggerCreator.Companion.configureFakeLogger
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class DeleteProductReviewTest {

    init {
        configureFakeLogger()
    }

    private val productReviewInMemoryRepository = ProductReviewInMemoryRepository()

    private val productReviewFinder = ProductReviewFinder(
        productReviewInMemoryRepository
    )

    private val eventBusInMemory = EventBusInMemory()

    private val deleteProductReview = DeleteProductReview(
        productReviewFinder,
        productReviewInMemoryRepository,
        eventBusInMemory
    )

    @Test
    fun `should delete product review when it exists`() = runTest {
        val oneProductReview = oneProductReview()
        productReviewInMemoryRepository.reset(oneProductReview)

        val userAuth = oneUserAuth()
        val params = DeleteProductReviewParams(
            userAuth,
            oneProductReview.id
        )

        val result = deleteProductReview.execute(params)

        assertAll(
            "check handler result",
            { assertEquals(oneProductReview, result) }
        )

        assertAll(
            "check repository",
            { assertTrue(productReviewInMemoryRepository.isEmpty) }
        )

        val expectedEvent = productReviewDeletedOf(oneProductReview)

        assertAll(
            "check product created event was published",
            { assertTrue(eventBusInMemory.containsNumElements(1)) },
            { assertEquals(expectedEvent, eventBusInMemory.first) }
        )
    }

    @Test
    fun `should fail when it doesn't exist`() = runTest {
        productReviewInMemoryRepository.reset()

        val userAuth = oneUserAuth()
        val oneProductReviewId = oneProductReviewId()
        val params = DeleteProductReviewParams(
            userAuth,
            oneProductReviewId
        )

        assertThrows<ProductReviewNotFoundException> {
            deleteProductReview.execute(params)
        }
    }

    @Test
    fun `should fail when anonymous user executes the handler`() = runTest {
        productReviewInMemoryRepository.reset()

        val userAuth = oneAnonymousAuth()
        val oneProductReviewId = oneProductReviewId()
        val params = DeleteProductReviewParams(
            userAuth,
            oneProductReviewId
        )

        assertThrows<UnauthorizedException> {
            deleteProductReview.execute(params)
        }
    }

    @Test
    fun `should fail when user doesn't have mandatory permissions`() = runTest {
        productReviewInMemoryRepository.reset()

        val userRoles = emptyList<String>()
        val userAuth = oneUserAuth(userRoles)
        val oneProductReviewId = oneProductReviewId()
        val params = DeleteProductReviewParams(
            userAuth,
            oneProductReviewId
        )

        assertThrows<ForbiddenException> {
            deleteProductReview.execute(params)
        }
    }
}