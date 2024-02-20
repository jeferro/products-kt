package com.jeferro.products.products.application

import com.jeferro.lib.domain.exceptions.ForbiddenException
import com.jeferro.lib.domain.exceptions.UnauthorizedException
import com.jeferro.lib.domain.models.metadata.Metadata
import com.jeferro.lib.domain.utils.time.FakeTimeService
import com.jeferro.products.products.domain.events.ProductUpserted
import com.jeferro.products.products.application.UpsertProductHandler
import com.jeferro.products.products.application.operations.UpsertProduct
import com.jeferro.products.products.domain.models.Product
import com.jeferro.products.products.domain.models.ProductIdMother.oneProductId
import com.jeferro.products.products.domain.models.ProductMother.oneProduct
import com.jeferro.products.products.domain.repositories.ProductsRepositoryInMemory
import com.jeferro.products.shared.domain.events.EventBusInMemory
import com.jeferro.products.shared.domain.models.auth.AuthMother.oneAnonymousAuth
import com.jeferro.products.shared.domain.models.auth.AuthMother.oneUserAuth
import com.jeferro.products.shared.domain.models.auth.UserIdMother.oneUserId
import com.jeferro.products.shared.domain.models.auth.UserIdMother.twoUserId
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import java.time.Instant
import java.time.temporal.ChronoUnit.MINUTES

class UpsertProductHandlerTest {

    private val productsRepositoryInMemory = ProductsRepositoryInMemory()

    private val eventBusInMemory = EventBusInMemory()

    private val upsertProductHandler = UpsertProductHandler(
        productsRepositoryInMemory,
        eventBusInMemory
    )

    @BeforeEach
    fun beforeEach() {
        eventBusInMemory.clear()
    }

    @Test
    fun `should create product when it doesn't exist`() = runTest {
        FakeTimeService.fakesNow()

        productsRepositoryInMemory.reset()

        val userAuth = oneUserAuth()
        val productId = oneProductId()
        val title = "title of new product"
        val description = "description of new product"
        val operation = UpsertProduct(
            userAuth,
            productId,
            title,
            description
        )

        val result = upsertProductHandler.execute(operation)

        val expectedProduct = Product(
            productId,
            title,
            description,
            true,
            Metadata.create(operation.userId)
        )

        assertAll(
            "check handler result",
            { assertEquals(expectedProduct, result) }
        )

        assertAll(
            "check product was saved in repository",
            { assertTrue(productsRepositoryInMemory.hasSize(1)) },
            { assertEquals(expectedProduct, productsRepositoryInMemory.first) }
        )

        val expectedEvent = ProductUpserted.create(expectedProduct)

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
        FakeTimeService.fakesDates(createdAt, updatedAt)

        val createdBy = oneUserId()
        val initialProduct = oneProduct(createdBy)
        productsRepositoryInMemory.reset(initialProduct)

        val updatedBy = twoUserId()
        val userAuth = oneUserAuth(updatedBy)
        val newTitle = "new title of new product"
        val newDescription = "new description of new product"
        val operation = UpsertProduct(
            userAuth,
            initialProduct.id,
            newTitle,
            newDescription
        )

        val result = upsertProductHandler.execute(operation)

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
            { assertTrue(productsRepositoryInMemory.hasSize(1)) },
            { assertEquals(expectedProduct, productsRepositoryInMemory.first) }
        )

        val expectedEvent = ProductUpserted.create(expectedProduct)

        assertAll(
            "check product upserted event was published",
            { assertTrue(eventBusInMemory.containsNumElements(1)) },
            { assertEquals(expectedEvent, eventBusInMemory.first) }
        )
    }

    @Test
    fun `should fail when anonymous user executes the handler`() = runTest {
        FakeTimeService.fakesNow()

        productsRepositoryInMemory.reset()

        val userAuth = oneAnonymousAuth()
        val productId = oneProductId()
        val title = "new title of new product"
        val description = "new description of new product"
        val operation = UpsertProduct(
            userAuth,
            productId,
            title,
            description
        )

        assertThrows<UnauthorizedException> {
            upsertProductHandler.execute(operation)
        }
    }

    @Test
    fun `should fail when user doesn't have mandatory permissions`() = runTest {
        FakeTimeService.fakesNow()

        productsRepositoryInMemory.reset()

        val userRoles = emptyList<String>()
        val userAuth = oneUserAuth(userRoles)
        val productId = oneProductId()
        val title = "new title of new product"
        val description = "new description of new product"
        val operation = UpsertProduct(
            userAuth,
            productId,
            title,
            description
        )

        assertThrows<ForbiddenException> {
            upsertProductHandler.execute(operation)
        }
    }

}