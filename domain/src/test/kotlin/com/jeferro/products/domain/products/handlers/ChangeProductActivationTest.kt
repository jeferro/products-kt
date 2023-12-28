package com.jeferro.products.domain.products.handlers

import com.jeferro.products.domain.products.entities.ProductIdMother.oneProductId
import com.jeferro.products.domain.products.entities.ProductMother.oneProduct
import com.jeferro.products.domain.products.events.ProductActivationChanged.Companion.productActivationChangedOf
import com.jeferro.products.domain.products.exceptions.ProductNotFoundException
import com.jeferro.products.domain.products.handlers.params.ChangeProductActivationParams
import com.jeferro.products.domain.products.repositories.ProductInMemoryRepository
import com.jeferro.products.domain.products.services.ProductFinder
import com.jeferro.products.domain.shared.entities.auth.AuthMother.oneAnonymousAuth
import com.jeferro.products.domain.shared.entities.auth.AuthMother.oneUserAuth
import com.jeferro.products.domain.shared.events.bus.EventBusInMemory
import com.jeferro.products.domain.shared.exceptions.ForbiddenException
import com.jeferro.products.domain.shared.exceptions.UnauthorizedException
import com.jeferro.products.domain.shared.services.logger.FakeLoggerCreator.Companion.configureFakeLogger
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class ChangeProductActivationTest {

    init {
        configureFakeLogger()
    }

    private val productInMemoryRepository = ProductInMemoryRepository()

    private val productFinder = ProductFinder(
        productInMemoryRepository
    )

    private val eventBusInMemory = EventBusInMemory()

    private val changeProductActivation = ChangeProductActivation(
        productFinder,
        productInMemoryRepository,
        eventBusInMemory
    )

    @Test
    fun `should change product activation when it exists`() = runTest {
        val expectedProduct = oneProduct(true)
        productInMemoryRepository.reset(expectedProduct)

        val userAuth = oneUserAuth()
        val params = ChangeProductActivationParams(
            userAuth,
            expectedProduct.id,
            false
        )

        val result = changeProductActivation.execute(params)

        assertAll(
            "check handler result",
            { assertEquals(expectedProduct, result) },
            { assertFalse(expectedProduct.isEnabled) },
            { assertTrue(expectedProduct.isDisabled) }
        )

        assertAll(
            "check repository",
            { assertTrue(productInMemoryRepository.hasSize(1)) },
            { assertEquals(expectedProduct, productInMemoryRepository.first) }
        )

        val expectedEvent = productActivationChangedOf(expectedProduct)

        assertAll(
            "check product created event was published",
            { assertTrue(eventBusInMemory.containsNumElements(1)) },
            { assertEquals(expectedEvent, eventBusInMemory.first) }
        )
    }

    @Test
    fun `should fail when it doesn't exist`() = runTest {
        productInMemoryRepository.reset()

        val userAuth = oneUserAuth()
        val oneProductId = oneProductId()
        val params = ChangeProductActivationParams(
            userAuth,
            oneProductId,
            false
        )

        assertThrows<ProductNotFoundException> {
            changeProductActivation.execute(params)
        }
    }

    @Test
    fun `should fail when anonymous user executes the handler`() = runTest {
        productInMemoryRepository.reset()

        val userAuth = oneAnonymousAuth()
        val oneProductId = oneProductId()
        val params = ChangeProductActivationParams(
            userAuth,
            oneProductId,
            false
        )

        assertThrows<UnauthorizedException> {
            changeProductActivation.execute(params)
        }
    }

    @Test
    fun `should fail when user doesn't have mandatory permissions`() = runTest {
        productInMemoryRepository.reset()

        val userRoles = emptyList<String>()
        val userAuth = oneUserAuth(userRoles)
        val oneProductId = oneProductId()
        val params = ChangeProductActivationParams(
            userAuth,
            oneProductId,
            false
        )

        assertThrows<ForbiddenException> {
            changeProductActivation.execute(params)
        }
    }

}