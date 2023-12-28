package com.jeferro.products.infrastructure.product_reviews.adapters.mongo.mappers

import com.jeferro.products.components.products.mongo.product_reviews.dtos.ProductReviewMongoDTOMother.oneProductReviewMongoDTO
import com.jeferro.products.domain.product_reviews.entities.ProductReviewMother.oneProductReview
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ProductReviewMongoMapperTest {

    private val productReviewMongoMapper = ProductReviewMongoMapper.instance

    @Test
    fun `should map entity to dto and reverse successfully`() {
        val productReview = oneProductReview()

        val productReviewDTO = productReviewMongoMapper.toDTO(productReview)
        val result = productReviewMongoMapper.toEntity(productReviewDTO)

        assertEquals(productReview, result)
    }

    @Test
    fun `should map dto to entity and reverse successfully`() {
        val productReviewDTO = oneProductReviewMongoDTO()

        val productReview = productReviewMongoMapper.toEntity(productReviewDTO)
        val result = productReviewMongoMapper.toDTO(productReview)

        assertEquals(productReviewDTO, result)
    }
}