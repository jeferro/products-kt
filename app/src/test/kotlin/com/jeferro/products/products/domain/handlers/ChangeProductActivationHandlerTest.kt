package com.jeferro.products.products.domain.handlers

import com.jeferro.lib.domain.exceptions.ForbiddenException
import com.jeferro.lib.domain.exceptions.UnauthorizedException
import com.jeferro.products.products.domain.events.ProductActivationChanged
import com.jeferro.products.products.domain.exceptions.ProductNotFoundException
import com.jeferro.products.products.domain.handlers.operations.ChangeProductActivation
import com.jeferro.products.products.domain.models.ProductIdMother.oneProductId
import com.jeferro.products.products.domain.models.ProductMother.oneProduct
import com.jeferro.products.products.domain.repositories.ProductRepositoryInMemory
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

    private val productRepositoryInMemory = ProductRepositoryInMemory()

    private val productFinder = ProductFinder(
        productRepositoryInMemory
    )

    private val eventBusInMemory = EventBusInMemory()

    private val changeProductActivationHandler = ChangeProductActivationHandler(
        productFinder,
        productRepositoryInMemory,
        eventBusInMemory
    )

    @BeforeEach
    fun beforeEach() {
        eventBusInMemory.clear()
    }

    @Test
    fun `should change product activation when it exists`() = runTest {
        val expectedProduct = oneProduct(true)
        productRepositoryInMemory.reset(expectedProduct)

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
            { assertTrue(productRepositoryInMemory.hasSize(1)) },
            { assertEquals(expectedProduct, productRepositoryInMemory.first) }
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
        productRepositoryInMemory.reset()

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
        productRepositoryInMemory.reset()

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
        productRepositoryInMemory.reset()

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