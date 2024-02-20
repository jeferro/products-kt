package com.jeferro.products.products.application

import com.jeferro.lib.domain.exceptions.ForbiddenException
import com.jeferro.lib.domain.exceptions.UnauthorizedException
import com.jeferro.products.domain.products.entities.Products
import com.jeferro.products.products.application.ListProductsHandler
import com.jeferro.products.products.application.operations.ListProducts
import com.jeferro.products.products.domain.models.ProductMother.oneProduct
import com.jeferro.products.products.domain.models.ProductMother.twoProduct
import com.jeferro.products.products.domain.repositories.ProductsRepositoryInMemory
import com.jeferro.products.shared.domain.models.auth.AuthMother.oneAnonymousAuth
import com.jeferro.products.shared.domain.models.auth.AuthMother.oneUserAuth
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class ListProductsHandlerTest {

    private val productsRepositoryInMemory = ProductsRepositoryInMemory()

    private val listProductsHandler = ListProductsHandler(
        productsRepositoryInMemory,
    )

    @Test
    fun `should list products`() = runTest {
        val oneProduct = oneProduct()
        val twoProduct = twoProduct()
        productsRepositoryInMemory.reset(oneProduct, twoProduct)

        val userAuth = oneUserAuth()
        val operation = ListProducts(
            userAuth
        )

        val result = listProductsHandler.execute(operation)

        val expectedProduct = Products.of(oneProduct, twoProduct)

        assertAll(
            "check handler result",
            { assertEquals(expectedProduct, result) }
        )
    }

    @Test
    fun `should fail when anonymous user executes the handler`() = runTest {
        productsRepositoryInMemory.reset()

        val userAuth = oneAnonymousAuth()
        val operation = ListProducts(
            userAuth
        )

        assertThrows<UnauthorizedException> {
            listProductsHandler.execute(operation)
        }
    }

    @Test
    fun `should fail when user doesn't have mandatory permissions`() = runTest {
        productsRepositoryInMemory.reset()

        val userRoles = emptyList<String>()
        val userAuth = oneUserAuth(userRoles)
        val operation = ListProducts(
            userAuth
        )

        assertThrows<ForbiddenException> {
            listProductsHandler.execute(operation)
        }
    }

}