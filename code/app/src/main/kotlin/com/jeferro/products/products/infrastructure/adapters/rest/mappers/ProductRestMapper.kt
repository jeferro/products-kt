package com.jeferro.products.products.infrastructure.adapters.rest.mappers

import com.jeferro.lib.infrastructure.shared.adapters.mappers.ToDTOMapper
import com.jeferro.products.accounts.infrastructure.adapters.rest.mappers.UserIdRestMapper
import com.jeferro.products.components.products.rest.dtos.ProductRestDTO
import com.jeferro.products.components.products.rest.dtos.ProductsRestDTO
import com.jeferro.products.domain.products.entities.Products
import com.jeferro.products.products.domain.models.Product
import com.jeferro.products.shared.infrastructure.adapters.rest.mappers.InstantRestMapper
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import org.springframework.http.ResponseEntity

@Mapper(
    uses = [
        ProductIdRestMapper::class,
        InstantRestMapper::class,
        UserIdRestMapper::class
    ]
)
abstract class ProductRestMapper : ToDTOMapper<Product, ProductRestDTO> {

    companion object {
        val instance = Mappers.getMapper(ProductRestMapper::class.java)
    }

    abstract override fun toDTO(entity: Product): ProductRestDTO

    fun toResponseDTO(product: Product): ResponseEntity<ProductRestDTO> {
        val dto = toDTO(product)

        return ResponseEntity.ok(dto)
    }

    fun toResponseDTOList(products: Products): ResponseEntity<ProductsRestDTO> {
        val dtos = products.map { product -> toDTO(product) }.toList()

        val productsDTO = ProductsRestDTO(dtos)

        return ResponseEntity.ok(productsDTO)
    }
}