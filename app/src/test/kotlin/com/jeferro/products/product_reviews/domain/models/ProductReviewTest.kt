package com.jeferro.products.product_reviews.domain.models

import com.jeferro.lib.domain.exceptions.ForbiddenException
import com.jeferro.lib.domain.models.metadata.Metadata
import com.jeferro.lib.domain.utils.time.FakeTimeService
import com.jeferro.products.product_reviews.domain.events.ProductReviewDeleted
import com.jeferro.products.product_reviews.domain.events.ProductReviewUpserted
import com.jeferro.products.product_reviews.domain.models.ProductReviewIdMother.oneProductReviewId
import com.jeferro.products.product_reviews.domain.models.ProductReviewMother.oneProductReview
import com.jeferro.products.shared.domain.models.auth.UserIdMother.oneUserId
import com.jeferro.products.shared.domain.models.auth.UserIdMother.twoUserId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class ProductReviewTest {

    @Nested
    inner class Constructor {

        @Test
        fun `should create product review`() {
            val expectedProductReview = oneProductReview()

            val result = ProductReview(
                expectedProductReview.id,
                expectedProductReview.comment,
                expectedProductReview.metadata
            )

            assertEquals(expectedProductReview, result)
        }
    }

    @Nested
    inner class NamedConstructors {

        @Test
        fun `should create product review of data`() {
            val oneProductReviewId = oneProductReviewId()
            val comment = "comment of the one product"
            val userId = oneUserId()

            val now = FakeTimeService.fakesNow()

            val result = ProductReview.create(
                oneProductReviewId,
                comment,
                userId
            )

            assertAll(
                "check product review data",
                { assertEquals(oneProductReviewId, result.id) },
                { assertEquals(comment, result.comment) },
                { assertEquals(now, result.createdAt) },
                { assertEquals(userId, result.createdBy) },
                { assertEquals(now, result.updatedAt) },
                { assertEquals(userId, result.updatedBy) }
            )

            val resultEvents = result.pullEvents()
            assertEquals(1, resultEvents.size)
            assertTrue(resultEvents[0] is ProductReviewUpserted)

            val event = resultEvents[0] as ProductReviewUpserted

            assertAll(
                "check event",
                { assertEquals(oneProductReviewId, event.productReviewId) },
                { assertEquals(userId, event.occurredBy) },
                { assertEquals(now, event.occurredOn) }
            )
        }

        @Test
        fun `should fail when product review doesn't belong to the user`() {
            val userId = oneUserId()
            val oneProductReviewId = oneProductReviewId(userId)

            val otherUserId = twoUserId()

            assertThrows<ForbiddenException> {
                ProductReview.create(
                    oneProductReviewId,
                    "comment of the one product",
                    otherUserId
                )
            }
        }
    }

    @Nested
    inner class Update {

        @Test
        fun `should update product review`() {
            val oneProductReviewId = oneProductReviewId()
            val initialComment = "comment of one product"
            val createdBy = oneUserId()

            val createdAt = FakeTimeService.fakesNow()

            val metadata = Metadata.create(createdBy)

            val productReview = ProductReview(
                oneProductReviewId,
                initialComment,
                metadata
            )

            val newComment = "new title one product"

            val updatedAt = FakeTimeService.fakesOneMinuteLater()

            productReview.update(
                newComment,
                createdBy
            )

            assertAll(
                "check product review data",
                { assertEquals(oneProductReviewId, productReview.id) },
                { assertEquals(newComment, productReview.comment) },
                { assertEquals(createdAt, productReview.createdAt) },
                { assertEquals(createdBy, productReview.createdBy) },
                { assertEquals(updatedAt, productReview.updatedAt) },
                { assertEquals(createdBy, productReview.updatedBy) }
            )
        }

        @Test
        fun `should fail when product review doesn't belong to the user`() {
            val createdBy = oneUserId()

            val productReview = oneProductReview(createdBy)

            FakeTimeService.fakesOneMinuteLater()

            val updatedBy = twoUserId()

            assertThrows<ForbiddenException> {
                productReview.update(
                    "new title one product",
                    updatedBy
                )
            }
        }
    }

    @Nested
    inner class Delete {

        @Test
        fun `should delete product review`() {
            val oneProductReviewId = oneProductReviewId()
            val comment = "comment of one product"
            val createdBy = oneUserId()

            val createdAt = FakeTimeService.fakesNow()

            val metadata = Metadata.create(createdBy)

            val productReview = ProductReview(
                oneProductReviewId,
                comment,
                metadata
            )

            val updatedAt = FakeTimeService.fakesOneMinuteLater()

            productReview.delete(createdBy)

            assertAll(
                "check product review metadata",
                { assertEquals(createdAt, productReview.createdAt) },
                { assertEquals(createdBy, productReview.createdBy) },
                { assertEquals(updatedAt, productReview.updatedAt) },
                { assertEquals(createdBy, productReview.updatedBy) }
            )

            val resultEvents = productReview.pullEvents()
            assertEquals(1, resultEvents.size)
            assertTrue(resultEvents[0] is ProductReviewDeleted)

            val event = resultEvents[0] as ProductReviewDeleted

            assertAll(
                "check event",
                { assertEquals(oneProductReviewId, event.productReviewId) },
                { assertEquals(createdBy, event.occurredBy) },
                { assertEquals(updatedAt, event.occurredOn) }
            )
        }

        @Test
        fun `should fail when product review doesn't belong to the user`() {
            val createdBy = oneUserId()

            val productReview = oneProductReview(createdBy)

            FakeTimeService.fakesOneMinuteLater()

            val updatedBy = twoUserId()

            assertThrows<ForbiddenException> {
                productReview.delete(updatedBy)
            }
        }
    }
}