package com.jeferro.products.infrastructure.products.adapters.rest.mappers

import com.jeferro.components.products.rest.dtos.ProductRestDTO
import com.jeferro.products.domain.products.entities.Product
import com.jeferro.products.domain.products.entities.ProductMother.oneProduct
import com.jeferro.products.domain.products.entities.ProductMother.twoProduct
import com.jeferro.products.domain.products.entities.Products
import com.jeferro.products.infrastructure.shared.components.rest.mappers.MetadataRestMapper
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class ProductRestMapperTest {

    private val productRestMapper = ProductRestMapper.instance

    private val productIdRestMapper = ProductIdRestMapper.instance

    private val metadataRestMapper = MetadataRestMapper.instance

    @Test
    fun `should map entity to response dto`() {
        val product = oneProduct()

        val result = productRestMapper.toResponseDTO(product).body

        assertProductDTO(product, result)
    }

    @Test
    fun `should map entities to response dto`() = runTest {
        val oneProduct = oneProduct()
        val twoProduct = twoProduct()
        val products = Products.of(oneProduct, twoProduct)

        val result = productRestMapper.toResponseDTOList(products).body?.items

        assertNotNull(result)
        assertProductDTO(oneProduct, result?.get(0))
        assertProductDTO(twoProduct, result?.get(1))
    }

    private fun assertProductDTO(
        expectedProduct: Product,
        dto: ProductRestDTO?
    ) {
        val expectedDTO = ProductRestDTO(
            productIdRestMapper.toDTO(expectedProduct.id),
            expectedProduct.title,
            expectedProduct.description,
            expectedProduct.isEnabled,
            metadataRestMapper.toDTO(expectedProduct.metadata)
        )

        assertEquals(expectedDTO, dto)
    }
}