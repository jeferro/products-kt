package com.jeferro.products.infrastructure.products.adapters.mongo

import com.jeferro.products.components.products.mongo.products.ProductsInMemoryMongoDao
import com.jeferro.products.components.products.mongo.products.dtos.ProductMongoDTOMother.oneProductMongoDTO
import com.jeferro.products.components.products.mongo.products.dtos.ProductMongoDTOMother.twoProductMongoDTO
import com.jeferro.products.domain.products.entities.ProductIdMother.oneProductId
import com.jeferro.products.domain.products.entities.ProductMother.oneProduct
import com.jeferro.products.infrastructure.products.adapters.mongo.mappers.ProductIdMongoMapper
import com.jeferro.products.infrastructure.products.adapters.mongo.mappers.ProductMongoMapper
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ProductsMongoRepositoryTest {

    private val productIdMongoMapper = ProductIdMongoMapper.instance

    private val productMongoMapper = ProductMongoMapper.instance

    private val productsInMemoryMongoDao = ProductsInMemoryMongoDao()

    private val productsMongoRepository = ProductsMongoRepository(
        productsInMemoryMongoDao
    )

    @Nested
    inner class Save {
        @Test
        fun `should save product when it doesn't exist`() = runTest {
            productsInMemoryMongoDao.reset()

            val product = oneProduct()
            productsMongoRepository.save(product)

            val productDTO = productMongoMapper.toDTO(product)

            assertEquals(productDTO, productsInMemoryMongoDao.first)
        }

        @Test
        fun `should save product when it exists`() = runTest {
            val productDTO = oneProductMongoDTO()
            productsInMemoryMongoDao.reset(productDTO)

            val product = productMongoMapper.toEntity(productDTO)

            productsMongoRepository.save(product)

            assertTrue(productsInMemoryMongoDao.hasSize(1))
            assertEquals(productDTO, productsInMemoryMongoDao.first)
        }
    }

    @Nested
    inner class FindById {

        @Test
        fun `should return product when it exists`() = runTest {
            val productDTO = oneProductMongoDTO()
            productsInMemoryMongoDao.reset(productDTO)

            val product = productMongoMapper.toEntity(productDTO)

            val result = productsMongoRepository.findById(product.id)

            assertEquals(product, result)
        }

        @Test
        fun `should return null when it doesn't exist`() = runTest {
            productsInMemoryMongoDao.reset()

            val productId = oneProductId()
            val result = productsMongoRepository.findById(productId)

            assertNull(result)
        }
    }

    @Nested
    inner class DeleteById {

        @Test
        fun `should delete product when it exists`() = runTest {
            val productDTO = oneProductMongoDTO()
            productsInMemoryMongoDao.reset(productDTO)

            val productId = productIdMongoMapper.toEntity(productDTO.id)

            productsMongoRepository.deleteById(productId)

            assertTrue(productsInMemoryMongoDao.isEmpty)
        }

        @Test
        fun `should do nothing when it doesn't exist`() = runTest {
            productsInMemoryMongoDao.reset()

            val productId = oneProductId()
            productsMongoRepository.deleteById(productId)

            assertTrue(productsInMemoryMongoDao.isEmpty)
        }
    }

    @Nested
    inner class FindAll {

        @Test
        fun `should return all products`() = runTest {
            val oneProductDTO = oneProductMongoDTO()
            val twoProductDTO = twoProductMongoDTO()
            productsInMemoryMongoDao.reset(oneProductDTO, twoProductDTO)

            val result = productsMongoRepository.findAll()

            val oneProduct = productMongoMapper.toEntity(oneProductDTO)
            val twoProduct = productMongoMapper.toEntity(twoProductDTO)

            assertTrue(result.contains(oneProduct))
            assertTrue(result.contains(twoProduct))
        }

        @Test
        fun `should return empty list when there is no product`() = runTest {
            productsInMemoryMongoDao.reset()

            val result = productsMongoRepository.findAll()

            assertTrue(result.isEmpty)
        }
    }
}