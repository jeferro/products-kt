package com.jeferro.products.domain.products.handlers

import com.jeferro.products.domain.products.entities.ProductIdMother.oneProductId
import com.jeferro.products.domain.products.entities.ProductMother.oneProduct
import com.jeferro.products.domain.products.exceptions.ProductNotFoundException
import com.jeferro.products.domain.products.handlers.params.GetProductParams
import com.jeferro.products.domain.products.repositories.ProductInMemoryRepository
import com.jeferro.products.domain.products.services.ProductFinder
import com.jeferro.products.domain.shared.entities.auth.AuthMother.oneAnonymousAuth
import com.jeferro.products.domain.shared.entities.auth.AuthMother.oneUserAuth
import com.jeferro.products.domain.shared.exceptions.ForbiddenException
import com.jeferro.products.domain.shared.exceptions.UnauthorizedException
import com.jeferro.products.domain.shared.services.logger.FakeLoggerCreator.Companion.configureFakeLogger
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class GetProductTest {

    init {
        configureFakeLogger()
    }

    private val productInMemoryRepository = ProductInMemoryRepository()

    private val productFinder = ProductFinder(
        productInMemoryRepository
    )

    private val getProduct = GetProduct(
        productFinder
    )

    @Test
    fun `should get product when it exists`() = runTest {
        val oneProduct = oneProduct()
        productInMemoryRepository.reset(oneProduct)

        val userAuth = oneUserAuth()
        val params = GetProductParams(
            userAuth,
            oneProduct.id
        )

        val result = getProduct.execute(params)

        assertAll(
            "check handler result",
            { assertEquals(oneProduct, result) }
        )
    }

    @Test
    fun `should throw error when it doesn't exist`() = runTest {
        productInMemoryRepository.reset()

        val userAuth = oneUserAuth()
        val oneProductId = oneProductId()
        val params = GetProductParams(
            userAuth,
            oneProductId
        )

        assertThrows<ProductNotFoundException> {
            getProduct.execute(params)
        }
    }

    @Test
    fun `should fail when anonymous user executes the handler`() = runTest {
        productInMemoryRepository.reset()

        val userAuth = oneAnonymousAuth()
        val oneProductId = oneProductId()
        val params = GetProductParams(
            userAuth,
            oneProductId
        )

        assertThrows<UnauthorizedException> {
            getProduct.execute(params)
        }
    }

    @Test
    fun `should fail when user doesn't have mandatory permissions`() = runTest {
        productInMemoryRepository.reset()

        val userRoles = emptyList<String>()
        val userAuth = oneUserAuth(userRoles)
        val oneProductId = oneProductId()
        val params = GetProductParams(
            userAuth,
            oneProductId
        )

        assertThrows<ForbiddenException> {
            getProduct.execute(params)
        }
    }

}