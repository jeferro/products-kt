package com.jeferro.products.product_reviews.application

import com.jeferro.lib.domain.exceptions.ForbiddenException
import com.jeferro.lib.domain.exceptions.UnauthorizedException
import com.jeferro.lib.domain.models.metadata.Metadata
import com.jeferro.lib.domain.utils.time.FakeTimeService
import com.jeferro.products.product_reviews.domain.events.ProductReviewUpserted
import com.jeferro.products.product_reviews.application.operations.UpsertProductReview
import com.jeferro.products.product_reviews.domain.models.ProductReview
import com.jeferro.products.product_reviews.domain.models.ProductReviewIdMother.oneProductReviewId
import com.jeferro.products.product_reviews.domain.models.ProductReviewMother.oneProductReview
import com.jeferro.products.product_reviews.domain.repositories.ProductReviewsRepositoryInMemory
import com.jeferro.products.shared.domain.events.EventBusInMemory
import com.jeferro.products.shared.domain.models.auth.AuthMother.oneAnonymousAuth
import com.jeferro.products.shared.domain.models.auth.AuthMother.oneUserAuth
import com.jeferro.products.shared.domain.models.auth.UserIdMother.oneUserId
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

    private val productReviewsRepositoryInMemory = ProductReviewsRepositoryInMemory()

    private val eventBusInMemory = EventBusInMemory()

    private val upsertProductReviewHandler = UpsertProductReviewHandler(
        productReviewsRepositoryInMemory,
        eventBusInMemory
    )

    @BeforeEach
    fun beforeEach() {
        eventBusInMemory.clear()
    }

    @Test
    fun `should create product review when it doesn't exist`() = runTest {
        FakeTimeService.fakesNow()

        productReviewsRepositoryInMemory.reset()

        val createdBy = oneUserId()
        val userAuth = oneUserAuth(createdBy)
        val productReviewId = oneProductReviewId(createdBy)
        val comment = "comment of new product"
        val params = UpsertProductReview(
            userAuth,
            productReviewId,
            comment
        )

        val result = upsertProductReviewHandler.execute(params)

        val expectedProductReview = ProductReview(
            productReviewId,
            comment,
            Metadata.create(params.userId)
        )

        assertAll(
            "check handler result",
            { assertEquals(expectedProductReview, result) }
        )

        assertAll(
            "check product review was saved in repository",
            { assertTrue(productReviewsRepositoryInMemory.hasSize(1)) },
            { assertEquals(expectedProductReview, productReviewsRepositoryInMemory.first) }
        )

        val expectedEvent = ProductReviewUpserted.create(expectedProductReview)

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
        FakeTimeService.fakesDates(createdAt, updatedAt)

        val createdBy = oneUserId()
        val initialProductReview = oneProductReview(createdBy)
        productReviewsRepositoryInMemory.reset(initialProductReview)

        val userAuth = oneUserAuth(createdBy)
        val newComment = "new comment of product"
        val params = UpsertProductReview(
            userAuth,
            initialProductReview.id,
            newComment
        )

        val result = upsertProductReviewHandler.execute(params)

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
            { assertTrue(productReviewsRepositoryInMemory.hasSize(1)) },
            { assertEquals(expectedProductReview, productReviewsRepositoryInMemory.first) }
        )

        val expectedEvent = ProductReviewUpserted.create(expectedProductReview)

        assertAll(
            "check product created event was published",
            { assertTrue(eventBusInMemory.containsNumElements(1)) },
            { assertEquals(expectedEvent, eventBusInMemory.first) }
        )
    }

    @Test
    fun `should fail when anonymous user executes the handler`() = runTest {
        FakeTimeService.fakesNow()

        productReviewsRepositoryInMemory.reset()

        val userAuth = oneAnonymousAuth()
        val productReviewId = oneProductReviewId()
        val comment = "comment of new product"
        val params = UpsertProductReview(
            userAuth,
            productReviewId,
            comment
        )

        assertThrows<UnauthorizedException> {
            upsertProductReviewHandler.execute(params)
        }
    }

    @Test
    fun `should fail when user doesn't have mandatory permissions`() = runTest {
        FakeTimeService.fakesNow()

        productReviewsRepositoryInMemory.reset()

        val userRoles = emptyList<String>()
        val userAuth = oneUserAuth(userRoles)
        val productReviewId = oneProductReviewId()
        val comment = "new comment of new product"
        val params = UpsertProductReview(
            userAuth,
            productReviewId,
            comment
        )

        assertThrows<ForbiddenException> {
            upsertProductReviewHandler.execute(params)
        }
    }

}