package com.jeferro.products.products.domain.models

import com.jeferro.lib.domain.models.metadata.Metadata
import com.jeferro.lib.domain.time.FakeTimeService
import com.jeferro.products.products.domain.events.ProductActivationChanged
import com.jeferro.products.products.domain.events.ProductDeleted
import com.jeferro.products.products.domain.events.ProductUpserted
import com.jeferro.products.products.domain.exceptions.DisabledProductException
import com.jeferro.products.products.domain.models.ProductIdMother.oneProductId
import com.jeferro.products.products.domain.models.ProductMother.oneProduct
import com.jeferro.products.shared.domain.models.auth.UserIdMother.oneUserId
import com.jeferro.products.shared.domain.models.auth.UserIdMother.twoUserId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class ProductTest {

    @Nested
    inner class ConstructorTests {

        @Test
        fun `should create product`() {
            val expectedProduct = oneProduct()

            val result = Product(
                expectedProduct.id,
                expectedProduct.title,
                expectedProduct.description,
                expectedProduct.isEnabled,
                expectedProduct.metadata
            )

            assertEquals(expectedProduct, result)
        }
    }

    @Nested
    inner class NamedConstructorsTests {

        @Test
        fun `should create product of data`() {
            val productId = oneProductId()
            val title = "title one product"
            val description = "description one product"
            val userId = oneUserId()

            val now = FakeTimeService.fakesNow()

            val result = Product.create(
                productId,
                title,
                description,
                userId
            )

            assertAll(
                "check product data",
                { assertEquals(productId, result.id) },
                { assertEquals(title, result.title) },
                { assertEquals(description, result.description) },
                { assertTrue(result.isEnabled) },
                { assertEquals(now, result.createdAt) },
                { assertEquals(userId, result.createdBy) },
                { assertEquals(now, result.updatedAt) },
                { assertEquals(userId, result.updatedBy) }
            )

            val resultEvents = result.pullEvents()
            assertEquals(1, resultEvents.size)
            assertTrue(resultEvents[0] is ProductUpserted)

            val event = resultEvents[0] as ProductUpserted

            assertAll(
                "check event",
                { assertEquals(productId, event.productId) },
                { assertEquals(userId, event.occurredBy) },
                { assertEquals(now, event.occurredOn) }
            )
        }
    }

    @Nested
    inner class UpdateTests {

        @Test
        fun `should update product`() {
            val productId = oneProductId()
            val initialTitle = "title one product"
            val initialDescription = "description one product"
            val createdBy = oneUserId()

            val createdAt = FakeTimeService.fakesNow()

            val metadata = Metadata.create(createdBy)

            val product = Product(
                productId,
                initialTitle,
                initialDescription,
                true,
                metadata
            )

            val newTitle = "new title one product"
            val newDescription = "new description one product"
            val updatedBy = twoUserId()

            val updatedAt = FakeTimeService.fakesOneMinuteLater()

            product.update(
                newTitle,
                newDescription,
                updatedBy
            )

            assertAll(
                "check product data",
                { assertEquals(productId, product.id) },
                { assertEquals(newTitle, product.title) },
                { assertEquals(newDescription, product.description) },
                { assertTrue(product.isEnabled) },
                { assertEquals(createdAt, product.createdAt) },
                { assertEquals(createdBy, product.createdBy) },
                { assertEquals(updatedAt, product.updatedAt) },
                { assertEquals(updatedBy, product.updatedBy) }

            )

        }

        @Test
        fun `should fail when product is disabled`() {
            val productId = oneProductId()
            val initialTitle = "title one product"
            val initialDescription = "description one product"
            val createdBy = oneUserId()

            FakeTimeService.fakesNow()

            val metadata = Metadata.create(createdBy)

            val product = Product(
                productId,
                initialTitle,
                initialDescription,
                false,
                metadata
            )

            val newTitle = "new title one product"
            val newDescription = "new description one product"
            val updatedBy = twoUserId()

            assertThrows<DisabledProductException> {
                product.update(
                    newTitle,
                    newDescription,
                    updatedBy
                )
            }
        }
    }

    @Nested
    inner class ChangeActivationTests {

        @Test
        fun `should change activation`() {
            val productId = oneProductId()
            val title = "title one product"
            val description = "description one product"
            val createdBy = oneUserId()

            val createdAt = FakeTimeService.fakesNow()
            val metadata = Metadata.create(createdBy)

            val product = Product(
                productId,
                title,
                description,
                true,
                metadata
            )

            val updatedBy = twoUserId()
            val updatedAt = FakeTimeService.fakesOneMinuteLater()

            product.changeActivation(
                false,
                updatedBy
            )

            assertAll(
                "check product metadata",
                { assertFalse(product.isEnabled) },
                { assertEquals(createdAt, product.createdAt) },
                { assertEquals(createdBy, product.createdBy) },
                { assertEquals(updatedAt, product.updatedAt) },
                { assertEquals(updatedBy, product.updatedBy) }
            )

            val resultEvents = product.pullEvents()
            assertEquals(1, resultEvents.size)
            assertTrue(resultEvents[0] is ProductActivationChanged)

            val event = resultEvents[0] as ProductActivationChanged

            assertAll(
                "check event",
                { assertEquals(productId, event.productId) },
                { assertEquals(updatedBy, event.occurredBy) },
                { assertEquals(updatedAt, event.occurredOn) }
            )
        }
    }

    @Nested
    inner class DeleteTests {

        @Test
        fun `should delete product`() {
            val productId = oneProductId()
            val title = "title one product"
            val description = "description one product"
            val createdBy = oneUserId()

            val createdAt = FakeTimeService.fakesNow()

            val metadata = Metadata.create(createdBy)

            val product = Product(
                productId,
                title,
                description,
                true,
                metadata
            )

            val updatedBy = twoUserId()
            val updatedAt = FakeTimeService.fakesOneMinuteLater()

            product.delete(updatedBy)

            assertAll(
                "check product metadata",
                { assertEquals(createdAt, product.createdAt) },
                { assertEquals(createdBy, product.createdBy) },
                { assertEquals(updatedAt, product.updatedAt) },
                { assertEquals(updatedBy, product.updatedBy) }
            )

            val resultEvents = product.pullEvents()
            assertEquals(1, resultEvents.size)
            assertTrue(resultEvents[0] is ProductDeleted)

            val event = resultEvents[0] as ProductDeleted

            assertAll(
                "check event",
                { assertEquals(productId, event.productId) },
                { assertEquals(updatedBy, event.occurredBy) },
                { assertEquals(updatedAt, event.occurredOn) }
            )
        }
    }
}