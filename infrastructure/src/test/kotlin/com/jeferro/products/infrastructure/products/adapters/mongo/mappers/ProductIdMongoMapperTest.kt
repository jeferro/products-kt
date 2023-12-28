package com.jeferro.products.infrastructure.products.adapters.mongo.mappers

import com.jeferro.products.domain.products.entities.ProductIdMother.oneProductId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ProductIdMongoMapperTest {

    private val productIdMongoMapper = ProductIdMongoMapper.instance

    @Test
    fun `should map entity to dto and reverse successfully`() {
        val productId = oneProductId()

        val productIdDTO = productIdMongoMapper.toDTO(productId)
        val result = productIdMongoMapper.toEntity(productIdDTO)

        assertEquals(productId, result)
    }

    @Test
    fun `should map dto to entity and reverse successfully`() {
        val productIdDTO = "one-product-id"

        val productId = productIdMongoMapper.toEntity(productIdDTO)
        val result = productIdMongoMapper.toDTO(productId)

        assertEquals(productIdDTO, result)
    }
}