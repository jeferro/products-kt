package com.jeferro.products.products.domain.handlers

import com.jeferro.lib.domain.exceptions.ForbiddenException
import com.jeferro.lib.domain.exceptions.UnauthorizedException
import com.jeferro.products.products.domain.exceptions.ProductNotFoundException
import com.jeferro.products.products.domain.handlers.operations.GetProduct
import com.jeferro.products.products.domain.models.ProductIdMother.oneProductId
import com.jeferro.products.products.domain.models.ProductMother.oneProduct
import com.jeferro.products.products.domain.repositories.ProductRepositoryInMemory
import com.jeferro.products.products.domain.services.ProductFinder
import com.jeferro.products.shared.domain.models.auth.AuthMother.oneAnonymousAuth
import com.jeferro.products.shared.domain.models.auth.AuthMother.oneUserAuth
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class GetProductHandlerTest {

    private val productRepositoryInMemory = ProductRepositoryInMemory()

    private val productFinder = ProductFinder(
        productRepositoryInMemory
    )

    private val getProductHandler = GetProductHandler(
        productFinder
    )

    @Test
    fun `should get product when it exists`() = runTest {
        val oneProduct = oneProduct()
        productRepositoryInMemory.reset(oneProduct)

        val userAuth = oneUserAuth()
        val operation = GetProduct(
            userAuth,
            oneProduct.id
        )

        val result = getProductHandler.execute(operation)

        assertAll(
            "check handler result",
            { assertEquals(oneProduct, result) }
        )
    }

    @Test
    fun `should throw error when it doesn't exist`() = runTest {
        productRepositoryInMemory.reset()

        val userAuth = oneUserAuth()
        val oneProductId = oneProductId()
        val operation = GetProduct(
            userAuth,
            oneProductId
        )

        assertThrows<ProductNotFoundException> {
            getProductHandler.execute(operation)
        }
    }

    @Test
    fun `should fail when anonymous user executes the handler`() = runTest {
        productRepositoryInMemory.reset()

        val userAuth = oneAnonymousAuth()
        val oneProductId = oneProductId()
        val operation = GetProduct(
            userAuth,
            oneProductId
        )

        assertThrows<UnauthorizedException> {
            getProductHandler.execute(operation)
        }
    }

    @Test
    fun `should fail when user doesn't have mandatory permissions`() = runTest {
        productRepositoryInMemory.reset()

        val userRoles = emptyList<String>()
        val userAuth = oneUserAuth(userRoles)
        val oneProductId = oneProductId()
        val operation = GetProduct(
            userAuth,
            oneProductId
        )

        assertThrows<ForbiddenException> {
            getProductHandler.execute(operation)
        }
    }

}