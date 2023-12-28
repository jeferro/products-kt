package com.jeferro.products.domain.products.handlers

import com.jeferro.products.domain.products.entities.ProductMother.oneProduct
import com.jeferro.products.domain.products.entities.ProductMother.twoProduct
import com.jeferro.products.domain.products.entities.Products
import com.jeferro.products.domain.products.handlers.params.ListProductsParams
import com.jeferro.products.domain.products.repositories.ProductInMemoryRepository
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

class ListProductsTest {

    init {
        configureFakeLogger()
    }

    private val productInMemoryRepository = ProductInMemoryRepository()

    private val listProducts = ListProducts(
        productInMemoryRepository,
    )

    @Test
    fun `should list products`() = runTest {
        val oneProduct = oneProduct()
        val twoProduct = twoProduct()
        productInMemoryRepository.reset(oneProduct, twoProduct)

        val userAuth = oneUserAuth()
        val params = ListProductsParams(
            userAuth
        )

        val result = listProducts.execute(params)

        val expectedProduct = Products.of(oneProduct, twoProduct)

        assertAll(
            "check handler result",
            { assertEquals(expectedProduct, result) }
        )
    }

    @Test
    fun `should fail when anonymous user executes the handler`() = runTest {
        productInMemoryRepository.reset()

        val userAuth = oneAnonymousAuth()
        val params = ListProductsParams(
            userAuth
        )

        assertThrows<UnauthorizedException> {
            listProducts.execute(params)
        }
    }

    @Test
    fun `should fail when user doesn't have mandatory permissions`() = runTest {
        productInMemoryRepository.reset()

        val userRoles = emptyList<String>()
        val userAuth = oneUserAuth(userRoles)
        val params = ListProductsParams(
            userAuth
        )

        assertThrows<ForbiddenException> {
            listProducts.execute(params)
        }
    }

}