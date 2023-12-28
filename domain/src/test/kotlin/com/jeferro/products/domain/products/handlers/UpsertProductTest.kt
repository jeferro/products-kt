package com.jeferro.products.domain.products.handlers

import com.jeferro.products.domain.products.entities.Product
import com.jeferro.products.domain.products.entities.ProductIdMother.oneProductId
import com.jeferro.products.domain.products.entities.ProductMother.oneProduct
import com.jeferro.products.domain.products.events.ProductUpserted.Companion.productUpsertedOf
import com.jeferro.products.domain.products.handlers.params.UpsertProductParams
import com.jeferro.products.domain.products.repositories.ProductInMemoryRepository
import com.jeferro.products.domain.shared.entities.auth.AuthMother.oneAnonymousAuth
import com.jeferro.products.domain.shared.entities.auth.AuthMother.oneUserAuth
import com.jeferro.products.domain.shared.entities.auth.UserIdMother.oneUserId
import com.jeferro.products.domain.shared.entities.auth.UserIdMother.twoUserId
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

class UpsertProductTest {

    init {
        configureFakeLogger()
    }

    private val productInMemoryRepository = ProductInMemoryRepository()

    private val eventBusInMemory = EventBusInMemory()

    private val upsertProduct = UpsertProduct(
        productInMemoryRepository,
        eventBusInMemory
    )

    @BeforeEach
    fun beforeEach() {
        eventBusInMemory.clear()
    }

    @Test
    fun `should create product when it doesn't exist`() = runTest {
        timeServiceFakesNow()

        productInMemoryRepository.reset()

        val userAuth = oneUserAuth()
        val productId = oneProductId()
        val title = "title of new product"
        val description = "description of new product"
        val params = UpsertProductParams(
            userAuth,
            productId,
            title,
            description
        )

        val result = upsertProduct.execute(params)

        val expectedProduct = Product(
            productId,
            title,
            description,
            true,
            metadataOf(params.userId)
        )

        assertAll(
            "check handler result",
            { assertEquals(expectedProduct, result) }
        )

        assertAll(
            "check product was saved in repository",
            { assertTrue(productInMemoryRepository.hasSize(1)) },
            { assertEquals(expectedProduct, productInMemoryRepository.first) }
        )

        val expectedEvent = productUpsertedOf(expectedProduct)

        assertAll(
            "check product upserted event was published",
            { assertTrue(eventBusInMemory.containsNumElements(1)) },
            { assertEquals(expectedEvent, eventBusInMemory.first) }
        )
    }

    @Test
    fun `should update product when it exists`() = runTest {
        val createdAt = Instant.now()
        val updatedAt = createdAt.plus(1, MINUTES)
        timeServiceFakesDates(createdAt, updatedAt)

        val createdBy = oneUserId()
        val initialProduct = oneProduct(createdBy)
        productInMemoryRepository.reset(initialProduct)

        val updatedBy = twoUserId()
        val userAuth = oneUserAuth(updatedBy)
        val newTitle = "new title of new product"
        val newDescription = "new description of new product"
        val params = UpsertProductParams(
            userAuth,
            initialProduct.id,
            newTitle,
            newDescription
        )

        val result = upsertProduct.execute(params)

        val expectedMetadata = Metadata(
            createdAt,
            createdBy,
            updatedAt,
            updatedBy
        )

        val expectedProduct = Product(
            initialProduct.id,
            newTitle,
            newDescription,
            true,
            expectedMetadata
        )

        assertAll(
            "check handler result",
            { assertEquals(expectedProduct, result) }
        )

        assertAll(
            "check product was saved in repository",
            { assertTrue(productInMemoryRepository.hasSize(1)) },
            { assertEquals(expectedProduct, productInMemoryRepository.first) }
        )

        val expectedEvent = productUpsertedOf(expectedProduct)

        assertAll(
            "check product upserted event was published",
            { assertTrue(eventBusInMemory.containsNumElements(1)) },
            { assertEquals(expectedEvent, eventBusInMemory.first) }
        )
    }

    @Test
    fun `should fail when anonymous user executes the handler`() = runTest {
        timeServiceFakesNow()

        productInMemoryRepository.reset()

        val userAuth = oneAnonymousAuth()
        val productId = oneProductId()
        val title = "new title of new product"
        val description = "new description of new product"
        val params = UpsertProductParams(
            userAuth,
            productId,
            title,
            description
        )

        assertThrows<UnauthorizedException> {
            upsertProduct.execute(params)
        }
    }

    @Test
    fun `should fail when user doesn't have mandatory permissions`() = runTest {
        timeServiceFakesNow()

        productInMemoryRepository.reset()

        val userRoles = emptyList<String>()
        val userAuth = oneUserAuth(userRoles)
        val productId = oneProductId()
        val title = "new title of new product"
        val description = "new description of new product"
        val params = UpsertProductParams(
            userAuth,
            productId,
            title,
            description
        )

        assertThrows<ForbiddenException> {
            upsertProduct.execute(params)
        }
    }

}