package com.jeferro.products.products.domain.services

import com.jeferro.products.products.domain.exceptions.ProductNotFoundException
import com.jeferro.products.products.domain.models.ProductIdMother.oneProductId
import com.jeferro.products.products.domain.models.ProductMother.oneProduct
import com.jeferro.products.products.domain.repositories.ProductsRepositoryInMemory
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProductFinderTest {

    private val productsRepository = ProductsRepositoryInMemory()

    private val productFinder = ProductFinder(
        productsRepository
    )

    @Test
    fun `should return product when it exists`() = runTest {
        val expectedProduct = oneProduct()

        productsRepository.reset(expectedProduct)

        val result = productFinder.findById(expectedProduct.id)

        assertEquals(expectedProduct, result)
    }

    @Test
    fun `should fail when product doesn't exist`() = runTest {
        productsRepository.reset()

        val productId = oneProductId()

        assertThrows<ProductNotFoundException> {
            productFinder.findById(productId)
        }
    }
}