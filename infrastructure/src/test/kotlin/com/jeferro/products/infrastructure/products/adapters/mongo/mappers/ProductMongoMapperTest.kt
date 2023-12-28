package com.jeferro.products.infrastructure.products.adapters.mongo.mappers

import com.jeferro.products.components.products.mongo.products.dtos.ProductMongoDTOMother.oneProductMongoDTO
import com.jeferro.products.domain.products.entities.ProductMother.oneProduct
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ProductMongoMapperTest {

    private val productMongoMapper = ProductMongoMapper.instance

    @Test
    fun `should map entity to dto and reverse successfully`() {
        val product = oneProduct()

        val productDTO = productMongoMapper.toDTO(product)
        val result = productMongoMapper.toEntity(productDTO)

        assertEquals(product, result)
    }

    @Test
    fun `should map dto to entity and reverse successfully`() {
        val productDTO = oneProductMongoDTO()

        val product = productMongoMapper.toEntity(productDTO)
        val result = productMongoMapper.toDTO(product)

        assertEquals(productDTO, result)
    }
}