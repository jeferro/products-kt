package com.jeferro.products.products.application

import com.jeferro.lib.domain.exceptions.ForbiddenException
import com.jeferro.lib.domain.exceptions.UnauthorizedException
import com.jeferro.products.products.domain.events.ProductDeleted
import com.jeferro.products.products.domain.exceptions.ProductNotFoundException
import com.jeferro.products.products.application.DeleteProductHandler
import com.jeferro.products.products.application.operations.DeleteProduct
import com.jeferro.products.products.domain.models.ProductIdMother.oneProductId
import com.jeferro.products.products.domain.models.ProductMother.oneProduct
import com.jeferro.products.products.domain.repositories.ProductsRepositoryInMemory
import com.jeferro.products.products.domain.services.ProductFinder
import com.jeferro.products.shared.domain.events.EventBusInMemory
import com.jeferro.products.shared.domain.models.auth.AuthMother.oneAnonymousAuth
import com.jeferro.products.shared.domain.models.auth.AuthMother.oneUserAuth
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class DeleteProductHandlerTest {

    private val productsRepositoryInMemory = ProductsRepositoryInMemory()

    private val productFinder = ProductFinder(
        productsRepositoryInMemory
    )

    private val eventBusInMemory = EventBusInMemory()

    private val deleteProductHandler = DeleteProductHandler(
        productFinder,
        productsRepositoryInMemory,
        eventBusInMemory
    )

    @BeforeEach
    fun beforeEach() {
        eventBusInMemory.clear()
    }

    @Test
    fun `should delete product when it exists`() = runTest {
        val oneProduct = oneProduct()
        productsRepositoryInMemory.reset(oneProduct)

        val userAuth = oneUserAuth()
        val operation = DeleteProduct(
            userAuth,
            oneProduct.id
        )

        val result = deleteProductHandler.execute(operation)

        assertAll(
            "check handler result",
            { assertEquals(oneProduct, result) }
        )

        assertAll(
            "check repository",
            { assertTrue(productsRepositoryInMemory.isEmpty) }
        )

        val expectedEvent = ProductDeleted.create(oneProduct)

        assertAll(
            "check product created event was published",
            { assertTrue(eventBusInMemory.containsNumElements(1)) },
            { assertEquals(expectedEvent, eventBusInMemory.first) }
        )
    }

    @Test
    fun `should fail when it doesn't exist`() = runTest {
        productsRepositoryInMemory.reset()

        val userAuth = oneUserAuth()
        val oneProductId = oneProductId()
        val operation = DeleteProduct(
            userAuth,
            oneProductId
        )

        assertThrows<ProductNotFoundException> {
            deleteProductHandler.execute(operation)
        }
    }

    @Test
    fun `should fail when anonymous user executes the handler`() = runTest {
        productsRepositoryInMemory.reset()

        val userAuth = oneAnonymousAuth()
        val oneProductId = oneProductId()
        val operation = DeleteProduct(
            userAuth,
            oneProductId
        )

        assertThrows<UnauthorizedException> {
            deleteProductHandler.execute(operation)
        }
    }

    @Test
    fun `should fail when user doesn't have mandatory permissions`() = runTest {
        productsRepositoryInMemory.reset()

        val userRoles = emptyList<String>()
        val userAuth = oneUserAuth(userRoles)
        val oneProductId = oneProductId()
        val operation = DeleteProduct(
            userAuth,
            oneProductId
        )

        assertThrows<ForbiddenException> {
            deleteProductHandler.execute(operation)
        }
    }

}