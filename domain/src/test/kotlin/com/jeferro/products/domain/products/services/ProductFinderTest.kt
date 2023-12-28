package com.jeferro.products.domain.products.services

import com.jeferro.products.domain.products.entities.ProductIdMother.oneProductId
import com.jeferro.products.domain.products.entities.ProductMother.oneProduct
import com.jeferro.products.domain.products.exceptions.ProductNotFoundException
import com.jeferro.products.domain.products.repositories.ProductInMemoryRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProductFinderTest {

    private val productsRepository = ProductInMemoryRepository()

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