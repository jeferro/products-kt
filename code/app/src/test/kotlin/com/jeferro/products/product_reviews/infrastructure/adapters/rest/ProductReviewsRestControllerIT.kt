package com.jeferro.products.product_reviews.infrastructure.adapters.rest

import com.jeferro.lib.application.bus.HandlerBus
import com.jeferro.lib.infrastructure.shared.security.services.AuthRestService
import com.jeferro.products.product_reviews.application.operations.DeleteProductReview
import com.jeferro.products.product_reviews.application.operations.UpsertProductReview
import com.jeferro.products.product_reviews.domain.models.ProductReview
import com.jeferro.products.product_reviews.domain.models.ProductReviewMother.oneProductReview
import com.jeferro.products.shared.domain.models.auth.AuthMother.oneUserAuth
import com.jeferro.products.shared.infrastructure.adapters.rest.RestITConfiguration
import com.jeferro.products.shared.infrastructure.adapters.rest.andExpectAll
import com.jeferro.products.shared.infrastructure.adapters.rest.asyncDispatch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(ProductReviewsRestController::class)
@Import(RestITConfiguration::class)
class ProductReviewsRestControllerIT {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var authRestService: AuthRestService

    @MockBean
    private lateinit var handlerBus: HandlerBus

    @Nested
    inner class UpsertProductReviewTest {

        @Test
        fun `should upsert product product`() = runTest {
            val auth = oneUserAuth()
            val authToken = authRestService.getAuthenticationToken(auth)

            val expectedProductReview = oneProductReview()
            val comment = "new comment"
            val expectedOperation = UpsertProductReview(
                auth,
                expectedProductReview.id,
                comment
            )
            whenever(handlerBus.execute(expectedOperation))
                .thenReturn(expectedProductReview)

            val requestContent = """{
                "text": "$comment"
            }"""
            val requestUrl = "/v1/products/${expectedProductReview.id.productId}/reviews/${expectedProductReview.id.userId}"
            val requestBuilder = MockMvcRequestBuilders.put(requestUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, authToken)
                .content(requestContent.trimIndent())

            mockMvc.perform(requestBuilder)
                .asyncDispatch(mockMvc)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(assertProductReviewRestDTO(expectedProductReview))
        }
    }

    @Nested
    inner class DeleteProductReviewTest {

        @Test
        fun `should delete product product`() = runTest {
            val auth = oneUserAuth()
            val authToken = authRestService.getAuthenticationToken(auth)

            val expectedProductReview = oneProductReview()
            val expectedOperation = DeleteProductReview(
                auth,
                expectedProductReview.id
            )
            whenever(handlerBus.execute(expectedOperation))
                .thenReturn(expectedProductReview)

            val requestUrl = "/v1/products/${expectedProductReview.id.productId}/reviews/${expectedProductReview.id.userId}"
            val requestBuilder = MockMvcRequestBuilders.delete(requestUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, authToken)

            mockMvc.perform(requestBuilder)
                .asyncDispatch(mockMvc)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(assertProductReviewRestDTO(expectedProductReview))
        }
    }

    private fun assertProductReviewRestDTO(
        expectedProductReview: ProductReview
    ): List<ResultMatcher> {
        return listOf(
            MockMvcResultMatchers.jsonPath("$.id.userId").value(expectedProductReview.id.userId.value),
            MockMvcResultMatchers.jsonPath("$.id.productId").value(expectedProductReview.id.productId.value),
            MockMvcResultMatchers.jsonPath("$.comment").value(expectedProductReview.comment)
        )
    }
}