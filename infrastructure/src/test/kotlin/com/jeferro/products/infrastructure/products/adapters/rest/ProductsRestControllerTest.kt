package com.jeferro.products.infrastructure.products.adapters.rest

import com.jeferro.components.products.rest.dtos.ChangeProductActivationInputRestDTO
import com.jeferro.components.products.rest.dtos.ProductRestDTO
import com.jeferro.components.products.rest.dtos.ProductsRestDTO
import com.jeferro.components.products.rest.dtos.UpsertProductInputRestDTO
import com.jeferro.products.domain.products.entities.Product
import com.jeferro.products.domain.products.entities.ProductMother.oneProduct
import com.jeferro.products.domain.products.entities.ProductMother.twoProduct
import com.jeferro.products.domain.products.entities.Products
import com.jeferro.products.domain.products.handlers.params.*
import com.jeferro.products.domain.shared.entities.auth.Auth
import com.jeferro.products.domain.shared.entities.auth.AuthMother.oneUserAuth
import com.jeferro.products.domain.shared.handlers.bus.HandlerBus
import com.jeferro.products.infrastructure.products.adapters.rest.mappers.ProductIdRestMapper
import com.jeferro.products.infrastructure.products.adapters.rest.mappers.ProductRestMapper
import com.jeferro.products.infrastructure.shared.security.AuthRestResolver
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.argumentCaptor
import org.springframework.http.ResponseEntity

@ExtendWith(MockitoExtension::class)
class ProductsRestControllerTest {

    private val productIdRestMapper = ProductIdRestMapper.instance

    private val productRestMapper = ProductRestMapper.instance

    @Mock
    private lateinit var authRestResolver: AuthRestResolver

    @Mock
    private lateinit var handlerBus: HandlerBus

    @InjectMocks
    private lateinit var productsRestController: ProductsRestController

    @Nested
    inner class ListProducts {

        @Test
        fun `should list products through handler bus`() = runTest {
            val expectedAuth = oneUserAuth()
            `when`(authRestResolver.resolve())
                .then { expectedAuth }

            val oneProduct = oneProduct()
            val twoProduct = twoProduct()
            val expectedProducts = Products.of(oneProduct, twoProduct)
            val paramsCaptor = argumentCaptor<ListProductsParams>()
            `when`(handlerBus.execute(paramsCaptor.capture()))
                .then { expectedProducts }

            val response = productsRestController.listProducts()

            assertParams(
                expectedAuth,
                paramsCaptor.firstValue
            )

            assertResponse(
                expectedProducts,
                response
            )
        }

        private fun assertParams(
            expectedAuth: Auth,
            capturedParams: ListProductsParams
        ) {
            val expectedParams = ListProductsParams(
                expectedAuth
            )

            assertEquals(expectedParams, capturedParams)
        }

        private fun assertResponse(
            expectedProducts: Products,
            response: ResponseEntity<ProductsRestDTO>
        ) {
            val dtos = response.body?.items
            val expectedDtos = expectedProducts.map { product -> productRestMapper.toDTO(product) }.toList()

            assertEquals(expectedDtos, dtos)
        }
    }

    @Nested
    inner class GetProduct {

        @Test
        fun `should get product through handler bus`() = runTest {
            val expectedAuth = oneUserAuth()
            `when`(authRestResolver.resolve())
                .then { expectedAuth }

            val expectedProduct = oneProduct()
            val paramsCaptor = argumentCaptor<GetProductParams>()
            `when`(handlerBus.execute(paramsCaptor.capture()))
                .then { expectedProduct }

            val response = productsRestController.getProduct(
                expectedProduct.id.value
            )

            assertParams(
                expectedAuth,
                expectedProduct,
                paramsCaptor.firstValue
            )

            assertProductResponse(
                expectedProduct,
                response
            )
        }

        private fun assertParams(
            expectedAuth: Auth,
            expectedProduct: Product,
            capturedParams: GetProductParams
        ) {
            val expectedParams = GetProductParams(
                expectedAuth,
                expectedProduct.id
            )

            assertEquals(expectedParams, capturedParams)
        }
    }

    @Nested
    inner class UpsertProduct {

        @Test
        fun `should upsert product through handler bus`() = runTest {
            val expectedAuth = oneUserAuth()
            `when`(authRestResolver.resolve())
                .then { expectedAuth }

            val expectedProduct = oneProduct()
            val paramsCaptor = argumentCaptor<UpsertProductParams>()
            `when`(handlerBus.execute(paramsCaptor.capture()))
                .then { expectedProduct }

            val productIdDTO = "one-product-id"
            val inputDTO = UpsertProductInputRestDTO(
                "title of the product",
                "description of the product"
            )
            val response = productsRestController.upsertProduct(productIdDTO, inputDTO)

            assertParams(
                expectedAuth,
                productIdDTO,
                inputDTO,
                paramsCaptor.firstValue
            )

            assertProductResponse(
                expectedProduct,
                response
            )
        }

        private fun assertParams(
            expectedAuth: Auth,
            productIdDTO: String,
            inputDTO: UpsertProductInputRestDTO,
            capturedParams: UpsertProductParams
        ) {
            val expectedParams = UpsertProductParams(
                expectedAuth,
                productIdRestMapper.toEntity(productIdDTO),
                inputDTO.title,
                inputDTO.description
            )

            assertEquals(expectedParams, capturedParams)
        }
    }

    @Nested
    inner class ChangeProductActivation {

        @Test
        fun `should change product activation through handler bus`() = runTest {
            val expectedAuth = oneUserAuth()
            `when`(authRestResolver.resolve())
                .then { expectedAuth }

            val expectedProduct = oneProduct()
            val paramsCaptor = argumentCaptor<ChangeProductActivationParams>()
            `when`(handlerBus.execute(paramsCaptor.capture()))
                .then { expectedProduct }

            val inputDTO = ChangeProductActivationInputRestDTO(
                false
            )
            val response = productsRestController.changeProductActivation(
                expectedProduct.id.value,
                inputDTO
            )

            assertParams(
                expectedAuth,
                expectedProduct,
                inputDTO,
                paramsCaptor.firstValue
            )

            assertProductResponse(
                expectedProduct,
                response
            )
        }

        private fun assertParams(
            expectedAuth: Auth,
            expectedProduct: Product,
            inputDTO: ChangeProductActivationInputRestDTO,
            capturedParams: ChangeProductActivationParams
        ) {
            val expectedParams = ChangeProductActivationParams(
                expectedAuth,
                expectedProduct.id,
                inputDTO.enabled
            )

            assertEquals(expectedParams, capturedParams)
        }
    }

    @Nested
    inner class DeleteProduct {

        @Test
        fun `should delete product through handler bus`() = runTest {
            val expectedAuth = oneUserAuth()
            `when`(authRestResolver.resolve())
                .then { expectedAuth }

            val expectedProduct = oneProduct()
            val paramsCaptor = argumentCaptor<DeleteProductParams>()
            `when`(handlerBus.execute(paramsCaptor.capture()))
                .then { expectedProduct }

            val response = productsRestController.deleteProduct(
                expectedProduct.id.value
            )

            assertParams(
                expectedAuth,
                expectedProduct,
                paramsCaptor.firstValue
            )

            assertProductResponse(
                expectedProduct,
                response
            )
        }

        private fun assertParams(
            expectedAuth: Auth,
            expectedProduct: Product,
            capturedParams: DeleteProductParams
        ) {
            val expectedParams = DeleteProductParams(
                expectedAuth,
                expectedProduct.id
            )

            assertEquals(expectedParams, capturedParams)
        }
    }

    private fun assertProductResponse(
        expectedProduct: Product,
        response: ResponseEntity<ProductRestDTO>
    ) {
        val expectedResponse = ResponseEntity.ok()
            .body(productRestMapper.toDTO(expectedProduct))

        assertEquals(expectedResponse, response)
    }

}