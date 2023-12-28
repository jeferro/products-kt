package com.jeferro.products.infrastructure.product_reviews.adapters.mongo.mappers

import com.jeferro.products.domain.product_reviews.entities.ProductReviewIdMother.oneProductReviewId
import com.jeferro.products.domain.shared.exceptions.ValueValidationException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProductReviewIdMongoMapperTest {

    private val productReviewIdMongoMapper = ProductReviewIdMongoMapper.instance

    @Test
    fun `should map entity to dto and reverse successfully`() {
        val productReviewId = oneProductReviewId()

        val productReviewIdDTO = productReviewIdMongoMapper.toDTO(productReviewId)
        val result = productReviewIdMongoMapper.toEntity(productReviewIdDTO)

        assertEquals(productReviewId, result)
    }

    @Test
    fun `should map dto to entity and reverse successfully`() {
        val productIdDTO = "one-product"
        val userIdDTO = "one-user"
        val productReviewIdDTO = "$productIdDTO:$userIdDTO"

        val productReviewId = productReviewIdMongoMapper.toEntity(productReviewIdDTO)
        val result = productReviewIdMongoMapper.toDTO(productReviewId)

        assertEquals(productReviewIdDTO, result)
    }

    @Test
    fun `should fail when user id is empty`() {
        val productIdDTO = "one-product"
        val productReviewIdDTO = "$productIdDTO:"

        assertThrows<ValueValidationException> {
            productReviewIdMongoMapper.toEntity(productReviewIdDTO)
        }
    }

    @Test
    fun `should fail when product id is empty`() {
        val userIdDTO = "one-user"
        val productReviewIdDTO = ":$userIdDTO"

        assertThrows<ValueValidationException> {
            productReviewIdMongoMapper.toEntity(productReviewIdDTO)
        }
    }

    @Test
    fun `should fail when separator doesn't exist`() {
        val productIdDTO = "one-product"
        val userIdDTO = "one-user"
        val productReviewIdDTO = "$productIdDTO$userIdDTO"

        assertThrows<IllegalArgumentException> {
            productReviewIdMongoMapper.toEntity(productReviewIdDTO)
        }
    }
}