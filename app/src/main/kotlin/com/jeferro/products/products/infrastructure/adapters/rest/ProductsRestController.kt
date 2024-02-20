package com.jeferro.products.products.infrastructure.adapters.rest

import com.jeferro.lib.application.bus.HandlerBus
import com.jeferro.lib.infrastructure.shared.security.services.AuthRestService
import com.jeferro.products.components.products.rest.apis.ProductsApi
import com.jeferro.products.components.products.rest.dtos.ChangeProductActivationInputRestDTO
import com.jeferro.products.components.products.rest.dtos.ProductRestDTO
import com.jeferro.products.components.products.rest.dtos.ProductsRestDTO
import com.jeferro.products.components.products.rest.dtos.UpsertProductInputRestDTO
import com.jeferro.products.products.application.operations.*
import com.jeferro.products.products.infrastructure.adapters.rest.mappers.ProductIdRestMapper
import com.jeferro.products.products.infrastructure.adapters.rest.mappers.ProductRestMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductsRestController(
    private val authRestService: AuthRestService,
    private val handlerBus: HandlerBus
) : ProductsApi {

    private val productIdRestMapper = ProductIdRestMapper.instance

    private val productRestMapper = ProductRestMapper.instance

    override suspend fun listProducts(): ResponseEntity<ProductsRestDTO> {
        val operation = ListProducts(
            authRestService.getAuthFromRequest()
        )

        val products = handlerBus.execute(operation)

        return productRestMapper.toResponseDTOList(products)
    }

    override suspend fun getProduct(productId: String): ResponseEntity<ProductRestDTO> {
        val operation = GetProduct(
            authRestService.getAuthFromRequest(),
            productIdRestMapper.toEntity(productId),
        )

        val product = handlerBus.execute(operation)

        return productRestMapper.toResponseDTO(product)
    }

    override suspend fun upsertProduct(
        productId: String,
        upsertProductInputRestDTO: UpsertProductInputRestDTO
    ): ResponseEntity<ProductRestDTO> {
        val operation = UpsertProduct(
            authRestService.getAuthFromRequest(),
            productIdRestMapper.toEntity(productId),
            upsertProductInputRestDTO.title,
            upsertProductInputRestDTO.description
        )

        val product = handlerBus.execute(operation)

        return productRestMapper.toResponseDTO(product)
    }

    override suspend fun changeProductActivation(
        productId: String,
        changeProductActivationInputRestDTO: ChangeProductActivationInputRestDTO
    ): ResponseEntity<ProductRestDTO> {
        val operation = ChangeProductActivation(
            authRestService.getAuthFromRequest(),
            productIdRestMapper.toEntity(productId),
            changeProductActivationInputRestDTO.enabled
        )

        val product = handlerBus.execute(operation)

        return productRestMapper.toResponseDTO(product)
    }

    override suspend fun deleteProduct(productId: String): ResponseEntity<ProductRestDTO> {
        val operation = DeleteProduct(
            authRestService.getAuthFromRequest(),
            productIdRestMapper.toEntity(productId),
        )

        val product = handlerBus.execute(operation)

        return productRestMapper.toResponseDTO(product)
    }
}