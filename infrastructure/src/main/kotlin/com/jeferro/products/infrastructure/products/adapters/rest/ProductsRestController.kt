package com.jeferro.products.infrastructure.products.adapters.rest

import com.jeferro.components.products.rest.apis.ProductsApi
import com.jeferro.components.products.rest.dtos.ChangeProductActivationInputRestDTO
import com.jeferro.components.products.rest.dtos.ProductRestDTO
import com.jeferro.components.products.rest.dtos.ProductsRestDTO
import com.jeferro.components.products.rest.dtos.UpsertProductInputRestDTO
import com.jeferro.products.domain.products.handlers.params.*
import com.jeferro.products.domain.shared.handlers.bus.HandlerBus
import com.jeferro.products.infrastructure.products.adapters.rest.mappers.ProductIdRestMapper
import com.jeferro.products.infrastructure.products.adapters.rest.mappers.ProductRestMapper
import com.jeferro.products.infrastructure.shared.security.AuthRestResolver
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductsRestController(
    private val authRestResolver: AuthRestResolver,
    private val handlerBus: HandlerBus
) : ProductsApi {

    private val productIdRestMapper = ProductIdRestMapper.instance

    private val productRestMapper = ProductRestMapper.instance

    override suspend fun listProducts(): ResponseEntity<ProductsRestDTO> {
        val params = ListProductsParams(
            authRestResolver.resolve()
        )

        val products = handlerBus.execute(params)

        return productRestMapper.toResponseDTOList(products)
    }

    override suspend fun getProduct(productId: String): ResponseEntity<ProductRestDTO> {
        val params = GetProductParams(
            authRestResolver.resolve(),
            productIdRestMapper.toEntity(productId),
        )

        val product = handlerBus.execute(params)

        return productRestMapper.toResponseDTO(product)
    }

    override suspend fun upsertProduct(
        productId: String,
        upsertProductInputRestDTO: UpsertProductInputRestDTO
    ): ResponseEntity<ProductRestDTO> {
        val params = UpsertProductParams(
            authRestResolver.resolve(),
            productIdRestMapper.toEntity(productId),
            upsertProductInputRestDTO.title,
            upsertProductInputRestDTO.description
        )

        val product = handlerBus.execute(params)

        return productRestMapper.toResponseDTO(product)
    }

    override suspend fun changeProductActivation(
        productId: String,
        changeProductActivationInputRestDTO: ChangeProductActivationInputRestDTO
    ): ResponseEntity<ProductRestDTO> {
        val params = ChangeProductActivationParams(
            authRestResolver.resolve(),
            productIdRestMapper.toEntity(productId),
            changeProductActivationInputRestDTO.enabled
        )

        val product = handlerBus.execute(params)

        return productRestMapper.toResponseDTO(product)
    }

    override suspend fun deleteProduct(productId: String): ResponseEntity<ProductRestDTO> {
        val params = DeleteProductParams(
            authRestResolver.resolve(),
            productIdRestMapper.toEntity(productId),
        )

        val product = handlerBus.execute(params)

        return productRestMapper.toResponseDTO(product)
    }
}