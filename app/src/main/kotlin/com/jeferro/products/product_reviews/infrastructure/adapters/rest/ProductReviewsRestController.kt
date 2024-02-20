package com.jeferro.products.product_reviews.infrastructure.adapters.rest

import com.jeferro.lib.application.bus.HandlerBus
import com.jeferro.lib.infrastructure.shared.security.services.AuthRestService
import com.jeferro.products.components.products.rest.apis.ProductReviewsApi
import com.jeferro.products.components.products.rest.dtos.ProductReviewRestDTO
import com.jeferro.products.components.products.rest.dtos.UpsertProductReviewInputRestDTO
import com.jeferro.products.product_reviews.application.operations.DeleteProductReview
import com.jeferro.products.product_reviews.application.operations.UpsertProductReview
import com.jeferro.products.product_reviews.infrastructure.adapters.rest.mappers.ProductReviewIdRestMapper
import com.jeferro.products.product_reviews.infrastructure.adapters.rest.mappers.ProductReviewRestMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductReviewsRestController(
    private val authRestService: AuthRestService,
    private val handlerBus: HandlerBus
) : ProductReviewsApi {

    private val productReviewIdRestMapper = ProductReviewIdRestMapper.instance

    private val productReviewRestMapper = ProductReviewRestMapper.instance

    override suspend fun upsertProductReview(
        productId: String,
        userId: String,
        upsertProductReviewInputRestDTO: UpsertProductReviewInputRestDTO
    ): ResponseEntity<ProductReviewRestDTO> {
        val operation = UpsertProductReview(
            authRestService.getAuthFromRequest(),
            productReviewIdRestMapper.toEntity(productId, userId),
            upsertProductReviewInputRestDTO.text
        )

        val productReview = handlerBus.execute(operation)

        return productReviewRestMapper.toResponseDTO(productReview)
    }

    override suspend fun deleteProductReview(productId: String, userId: String): ResponseEntity<ProductReviewRestDTO> {
        val operation = DeleteProductReview(
            authRestService.getAuthFromRequest(),
            productReviewIdRestMapper.toEntity(productId, userId)
        )

        val productReview = handlerBus.execute(operation)

        return productReviewRestMapper.toResponseDTO(productReview)
    }
}