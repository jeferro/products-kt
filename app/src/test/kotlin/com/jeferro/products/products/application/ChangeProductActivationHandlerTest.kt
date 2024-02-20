package com.jeferro.products.products.application

import com.jeferro.lib.domain.exceptions.ForbiddenException
import com.jeferro.lib.domain.exceptions.UnauthorizedException
import com.jeferro.products.products.domain.events.ProductActivationChanged
import com.jeferro.products.products.domain.exceptions.ProductNotFoundException
import com.jeferro.products.products.application.ChangeProductActivationHandler
import com.jeferro.products.products.application.operations.ChangeProductActivation
import com.jeferro.products.products.domain.models.ProductIdMother.oneProductId
import com.jeferro.products.products.domain.models.ProductMother.oneProduct
import com.jeferro.products.products.domain.repositories.ProductsRepositoryInMemory
import com.jeferro.products.products.domain.services.ProductFinder
import com.jeferro.products.shared.domain.events.EventBusInMemory
import com.jeferro.products.shared.domain.models.auth.AuthMother.oneAnonymousAuth
import com.jeferro.products.shared.domain.models.auth.AuthMother.oneUserAuth
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class ChangeProductActivationHandlerTest {

    private val productsRepositoryInMemory = ProductsRepositoryInMemory()

    private val productFinder = ProductFinder(
        productsRepositoryInMemory
    )

    private val eventBusInMemory = EventBusInMemory()

    private val changeProductActivationHandler = ChangeProductActivationHandler(
        productFinder,
        productsRepositoryInMemory,
        eventBusInMemory
    )

    @BeforeEach
    fun beforeEach() {
        eventBusInMemory.clear()
    }

    @Test
    fun `should change product activation when it exists`() = runTest {
        val expectedProduct = oneProduct(true)
        productsRepositoryInMemory.reset(expectedProduct)

        val userAuth = oneUserAuth()
        val operation = ChangeProductActivation(
            userAuth,
            expectedProduct.id,
            false
        )

        val result = changeProductActivationHandler.execute(operation)

        assertAll(
            "check handler result",
            { assertEquals(expectedProduct, result) },
            { assertFalse(expectedProduct.isEnabled) },
            { assertTrue(expectedProduct.isDisabled) }
        )

        assertAll(
            "check repository",
            { assertTrue(productsRepositoryInMemory.hasSize(1)) },
            { assertEquals(expectedProduct, productsRepositoryInMemory.first) }
        )

        val expectedEvent = ProductActivationChanged.create(expectedProduct)

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
        val operation = ChangeProductActivation(
            userAuth,
            oneProductId,
            false
        )

        assertThrows<ProductNotFoundException> {
            changeProductActivationHandler.execute(operation)
        }
    }

    @Test
    fun `should fail when anonymous user executes the handler`() = runTest {
        productsRepositoryInMemory.reset()

        val userAuth = oneAnonymousAuth()
        val oneProductId = oneProductId()
        val operation = ChangeProductActivation(
            userAuth,
            oneProductId,
            false
        )

        assertThrows<UnauthorizedException> {
            changeProductActivationHandler.execute(operation)
        }
    }

    @Test
    fun `should fail when user doesn't have mandatory permissions`() = runTest {
        productsRepositoryInMemory.reset()

        val userRoles = emptyList<String>()
        val userAuth = oneUserAuth(userRoles)
        val oneProductId = oneProductId()
        val operation = ChangeProductActivation(
            userAuth,
            oneProductId,
            false
        )

        assertThrows<ForbiddenException> {
            changeProductActivationHandler.execute(operation)
        }
    }

}