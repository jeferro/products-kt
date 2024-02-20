package com.jeferro.products.products.application

import com.jeferro.lib.domain.exceptions.ForbiddenException
import com.jeferro.lib.domain.exceptions.UnauthorizedException
import com.jeferro.products.products.domain.exceptions.ProductNotFoundException
import com.jeferro.products.products.application.operations.GetProduct
import com.jeferro.products.products.domain.models.ProductIdMother.oneProductId
import com.jeferro.products.products.domain.models.ProductMother.oneProduct
import com.jeferro.products.products.domain.repositories.ProductsRepositoryInMemory
import com.jeferro.products.products.domain.services.ProductFinder
import com.jeferro.products.shared.domain.models.auth.AuthMother.oneAnonymousAuth
import com.jeferro.products.shared.domain.models.auth.AuthMother.oneUserAuth
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class GetProductHandlerTest {

    private val productsRepositoryInMemory = ProductsRepositoryInMemory()

    private val productFinder = ProductFinder(
        productsRepositoryInMemory
    )

    private val getProductHandler = GetProductHandler(
        productFinder
    )

    @Test
    fun `should get product when it exists`() = runTest {
        val oneProduct = oneProduct()
        productsRepositoryInMemory.reset(oneProduct)

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
        productsRepositoryInMemory.reset()

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
        productsRepositoryInMemory.reset()

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
        productsRepositoryInMemory.reset()

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