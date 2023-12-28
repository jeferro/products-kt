package com.jeferro.products.infrastructure.products.adapters.rest.mappers

import com.jeferro.products.domain.products.entities.ProductIdMother.oneProductId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ProductIdRestMapperTest {

    private val productIdRestMapper = ProductIdRestMapper.instance

    @Test
    fun `should map entity to dto and reverse successfully`() {
        val productId = oneProductId()

        val productIdDTO = productIdRestMapper.toDTO(productId)
        val result = productIdRestMapper.toEntity(productIdDTO)

        assertEquals(productId, result)
    }

    @Test
    fun `should map dto to entity and reverse successfully`() {
        val productIdDTO = "one-product-id"

        val productId = productIdRestMapper.toEntity(productIdDTO)
        val result = productIdRestMapper.toDTO(productId)

        assertEquals(productIdDTO, result)
    }
}