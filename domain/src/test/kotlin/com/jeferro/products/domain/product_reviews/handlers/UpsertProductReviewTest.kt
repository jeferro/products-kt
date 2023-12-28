package com.jeferro.products.domain.product_reviews.handlers

import com.jeferro.products.domain.product_reviews.entities.ProductReview
import com.jeferro.products.domain.product_reviews.entities.ProductReviewIdMother.oneProductReviewId
import com.jeferro.products.domain.product_reviews.entities.ProductReviewMother.oneProductReview
import com.jeferro.products.domain.product_reviews.events.ProductReviewUpserted.Companion.productReviewUpsertedOf
import com.jeferro.products.domain.product_reviews.handlers.params.UpsertProductReviewParams
import com.jeferro.products.domain.product_reviews.repositories.ProductReviewInMemoryRepository
import com.jeferro.products.domain.shared.entities.auth.AuthMother.oneAnonymousAuth
import com.jeferro.products.domain.shared.entities.auth.AuthMother.oneUserAuth
import com.jeferro.products.domain.shared.entities.auth.UserIdMother.oneUserId
import com.jeferro.products.domain.shared.entities.metadata.Metadata
import com.jeferro.products.domain.shared.entities.metadata.Metadata.Companion.metadataOf
import com.jeferro.products.domain.shared.events.bus.EventBusInMemory
import com.jeferro.products.domain.shared.exceptions.ForbiddenException
import com.jeferro.products.domain.shared.exceptions.UnauthorizedException
import com.jeferro.products.domain.shared.services.logger.FakeLoggerCreator.Companion.configureFakeLogger
import com.jeferro.products.domain.shared.services.time.FakeTimeService.Companion.timeServiceFakesDates
import com.jeferro.products.domain.shared.services.time.FakeTimeService.Companion.timeServiceFakesNow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import java.time.Instant
import java.time.temporal.ChronoUnit.MINUTES

class UpsertProductReviewTest {

    init {
        configureFakeLogger()
    }

    private val productReviewInMemoryRepository = ProductReviewInMemoryRepository()

    private val eventBusInMemory = EventBusInMemory()

    private val upsertProductReview = UpsertProductReview(
        productReviewInMemoryRepository,
        eventBusInMemory
    )

    @BeforeEach
    fun beforeEach() {
        eventBusInMemory.clear()
    }

    @Test
    fun `should create product review when it doesn't exist`() = runTest {
        timeServiceFakesNow()

        productReviewInMemoryRepository.reset()

        val createdBy = oneUserId()
        val userAuth = oneUserAuth(createdBy)
        val productReviewId = oneProductReviewId(createdBy)
        val comment = "comment of new product"
        val params = UpsertProductReviewParams(
            userAuth,
            productReviewId,
            comment
        )

        val result = upsertProductReview.execute(params)

        val expectedProductReview = ProductReview(
            productReviewId,
            comment,
            metadataOf(params.userId)
        )

        assertAll(
            "check handler result",
            { assertEquals(expectedProductReview, result) }
        )

        assertAll(
            "check product review was saved in repository",
            { assertTrue(productReviewInMemoryRepository.hasSize(1)) },
            { assertEquals(expectedProductReview, productReviewInMemoryRepository.first) }
        )

        val expectedEvent = productReviewUpsertedOf(expectedProductReview)

        assertAll(
            "check product review upserted event was published",
            { assertTrue(eventBusInMemory.containsNumElements(1)) },
            { assertEquals(expectedEvent, eventBusInMemory.first) }
        )
    }

    @Test
    fun `should update product review when it exists`() = runTest {
        val createdAt = Instant.now()
        val updatedAt = createdAt.plus(1, MINUTES)
        timeServiceFakesDates(createdAt, updatedAt)

        val createdBy = oneUserId()
        val initialProductReview = oneProductReview(createdBy)
        productReviewInMemoryRepository.reset(initialProductReview)

        val userAuth = oneUserAuth(createdBy)
        val newComment = "new comment of product"
        val params = UpsertProductReviewParams(
            userAuth,
            initialProductReview.id,
            newComment
        )

        val result = upsertProductReview.execute(params)

        val expectedMetadata = Metadata(
            createdAt,
            createdBy,
            updatedAt,
            createdBy
        )

        val expectedProductReview = ProductReview(
            initialProductReview.id,
            newComment,
            expectedMetadata
        )

        assertAll(
            "check handler result",
            { assertEquals(expectedProductReview, result) }
        )

        assertAll(
            "check product review was saved in repository",
            { assertTrue(productReviewInMemoryRepository.hasSize(1)) },
            { assertEquals(expectedProductReview, productReviewInMemoryRepository.first) }
        )

        val expectedEvent = productReviewUpsertedOf(expectedProductReview)

        assertAll(
            "check product created event was published",
            { assertTrue(eventBusInMemory.containsNumElements(1)) },
            { assertEquals(expectedEvent, eventBusInMemory.first) }
        )
    }

    @Test
    fun `should fail when anonymous user executes the handler`() = runTest {
        timeServiceFakesNow()

        productReviewInMemoryRepository.reset()

        val userAuth = oneAnonymousAuth()
        val productReviewId = oneProductReviewId()
        val comment = "comment of new product"
        val params = UpsertProductReviewParams(
            userAuth,
            productReviewId,
            comment
        )

        assertThrows<UnauthorizedException> {
            upsertProductReview.execute(params)
        }
    }

    @Test
    fun `should fail when user doesn't have mandatory permissions`() = runTest {
        timeServiceFakesNow()

        productReviewInMemoryRepository.reset()

        val userRoles = emptyList<String>()
        val userAuth = oneUserAuth(userRoles)
        val productReviewId = oneProductReviewId()
        val comment = "new comment of new product"
        val params = UpsertProductReviewParams(
            userAuth,
            productReviewId,
            comment
        )

        assertThrows<ForbiddenException> {
            upsertProductReview.execute(params)
        }
    }

}