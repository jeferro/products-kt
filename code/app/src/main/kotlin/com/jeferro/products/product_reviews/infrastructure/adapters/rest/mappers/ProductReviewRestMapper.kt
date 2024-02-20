package com.jeferro.products.product_reviews.infrastructure.adapters.rest.mappers

import com.jeferro.lib.infrastructure.shared.adapters.mappers.ToDTOMapper
import com.jeferro.products.components.products.rest.dtos.ProductReviewRestDTO
import com.jeferro.products.product_reviews.domain.models.ProductReview
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import org.springframework.http.ResponseEntity

@Mapper(
    uses = [
        ProductReviewIdRestMapper::class
    ]
)
abstract class ProductReviewRestMapper : ToDTOMapper<ProductReview, ProductReviewRestDTO> {

    companion object {
        val instance = Mappers.getMapper(ProductReviewRestMapper::class.java)
    }

    fun toResponseDTO(productReview: ProductReview): ResponseEntity<ProductReviewRestDTO> {
        val dto = toDTO(productReview)

        return ResponseEntity.ok(dto)
    }
}