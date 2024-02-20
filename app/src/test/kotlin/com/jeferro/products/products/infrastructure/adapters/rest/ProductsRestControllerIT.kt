package com.jeferro.products.products.infrastructure.adapters.rest

import com.jeferro.lib.domain.handlers.bus.HandlerBus
import com.jeferro.lib.infrastructure.shared.security.services.AuthRestService
import com.jeferro.products.domain.products.entities.Products
import com.jeferro.products.products.domain.handlers.operations.*
import com.jeferro.products.products.domain.models.Product
import com.jeferro.products.products.domain.models.ProductMother.oneProduct
import com.jeferro.products.products.domain.models.ProductMother.twoProduct
import com.jeferro.products.shared.domain.models.auth.AuthMother.oneUserAuth
import com.jeferro.products.shared.infrastructure.adapters.rest.RestITConfiguration
import com.jeferro.products.shared.infrastructure.adapters.rest.andExpectAll
import com.jeferro.products.shared.infrastructure.adapters.rest.asyncDispatch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.ZoneOffset

@WebMvcTest(ProductsRestController::class)
@Import(RestITConfiguration::class)
class ProductsRestControllerIT {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var authRestService: AuthRestService

    @MockBean
    private lateinit var handlerBus: HandlerBus

    @Nested
    inner class ListProductsTest {

        @Test
        fun `should list products`() = runTest {
            val auth = oneUserAuth()
            val authToken = authRestService.getAuthenticationToken(auth)

            val expectedOperation = ListProducts(auth)
            val oneProduct = oneProduct()
            val twoProduct = twoProduct()
            val products = Products.of(oneProduct, twoProduct)
            whenever(handlerBus.execute(expectedOperation))
                .thenReturn(products)

            val requestBuilder = MockMvcRequestBuilders.get("/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, authToken)

            mockMvc.perform(requestBuilder)
                .asyncDispatch(mockMvc)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items").isNotEmpty())
                .andExpect(jsonPath("$.items[0].id").value(oneProduct.id.value))
                .andExpect(jsonPath("$.items[1].id").value(twoProduct.id.value))
        }

        @Test
        fun `should return empty list of products`() = runTest {
            val auth = oneUserAuth()
            val authToken = authRestService.getAuthenticationToken(auth)

            val expectedOperation = ListProducts(auth)
            val products = Products.empty()
            whenever(handlerBus.execute(expectedOperation))
                .thenReturn(products)

            val requestBuilder = MockMvcRequestBuilders.get("/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, authToken)

            mockMvc.perform(requestBuilder)
                .asyncDispatch(mockMvc)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items").isEmpty())
        }
    }

    @Nested
    inner class GetProductTest {

        @Test
        fun `should get product`() = runTest {
            val auth = oneUserAuth()
            val authToken = authRestService.getAuthenticationToken(auth)

            val expectedProduct = oneProduct()
            val expectedOperation = GetProduct(auth, expectedProduct.id)
            whenever(handlerBus.execute(expectedOperation))
                .thenReturn(expectedProduct)

            val requestBuilder = MockMvcRequestBuilders.get("/v1/products/${expectedProduct.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, authToken)

            mockMvc.perform(requestBuilder)
                .asyncDispatch(mockMvc)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(assertProductRestDTO(expectedProduct))
        }
    }

    @Nested
    inner class UpsertProductTest {

        @Test
        fun `should get product`() = runTest {
            val auth = oneUserAuth()
            val authToken = authRestService.getAuthenticationToken(auth)

            val expectedProduct = oneProduct()
            val title = "new title"
            val description = "new description"
            val expectedOperation = UpsertProduct(
                auth,
                expectedProduct.id,
                title,
                description
            )
            whenever(handlerBus.execute(expectedOperation))
                .thenReturn(expectedProduct)

            val requestContent = """{
                "title": "$title",
                "description": "$description"
            }""".trimIndent()
            val requestBuilder = MockMvcRequestBuilders.put("/v1/products/${expectedProduct.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, authToken)
                .content(requestContent)

            mockMvc.perform(requestBuilder)
                .asyncDispatch(mockMvc)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(assertProductRestDTO(expectedProduct))
        }
    }

    @Nested
    inner class ChangeProductActivationTests {

        @Test
        fun `should enabled product`() = runTest {
            val auth = oneUserAuth()
            val authToken = authRestService.getAuthenticationToken(auth)

            val expectedProduct = oneProduct()
            val enabled = true
            val expectedOperation = ChangeProductActivation(auth, expectedProduct.id, enabled)
            whenever(handlerBus.execute(expectedOperation))
                .thenReturn(expectedProduct)

            val requestContent = """{
                "enabled": "$enabled"            
            }"""
            val requestBuilder = MockMvcRequestBuilders.put("/v1/products/${expectedProduct.id}/activation")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, authToken)
                .content(requestContent)

            mockMvc.perform(requestBuilder)
                .asyncDispatch(mockMvc)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(assertProductRestDTO(expectedProduct))
        }

        @Test
        fun `should disable product`() = runTest {
            val auth = oneUserAuth()
            val authToken = authRestService.getAuthenticationToken(auth)

            val expectedProduct = oneProduct()
            val enabled = false
            val expectedOperation = ChangeProductActivation(auth, expectedProduct.id, enabled)
            whenever(handlerBus.execute(expectedOperation))
                .thenReturn(expectedProduct)

            val requestContent = """{
                "enabled": "$enabled"            
            }"""
            val requestBuilder = MockMvcRequestBuilders.put("/v1/products/${expectedProduct.id}/activation")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, authToken)
                .content(requestContent)

            mockMvc.perform(requestBuilder)
                .asyncDispatch(mockMvc)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(assertProductRestDTO(expectedProduct))
        }
    }

    @Nested
    inner class DeleteProductTests {

        @Test
        fun `should delete product`() = runTest {
            val auth = oneUserAuth()
            val authToken = authRestService.getAuthenticationToken(auth)

            val expectedProduct = oneProduct()
            val expectedOperation = DeleteProduct(auth, expectedProduct.id)
            whenever(handlerBus.execute(expectedOperation))
                .thenReturn(expectedProduct)

            val requestBuilder = MockMvcRequestBuilders.delete("/v1/products/${expectedProduct.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, authToken)

            mockMvc.perform(requestBuilder)
                .asyncDispatch(mockMvc)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(assertProductRestDTO(expectedProduct))
        }
    }

    private fun assertProductRestDTO(
        expectedProduct: Product
    ): List<ResultMatcher> {
        return listOf(
            jsonPath("$.id").value(expectedProduct.id.value),
            jsonPath("$.title").value(expectedProduct.title),
            jsonPath("$.description").value(expectedProduct.description),
            jsonPath("$.enabled").value(expectedProduct.isEnabled)
        )
    }
}