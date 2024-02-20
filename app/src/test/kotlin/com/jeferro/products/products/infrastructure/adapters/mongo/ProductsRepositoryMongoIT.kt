package com.jeferro.products.products.infrastructure.adapters.mongo

import com.jeferro.products.products.domain.models.Product
import com.jeferro.products.products.domain.models.ProductIdMother.oneProductId
import com.jeferro.products.products.domain.models.ProductMother.oneProduct
import com.jeferro.products.products.domain.models.ProductMother.twoProduct
import com.jeferro.products.products.infrastructure.adapters.mongo.daos.ProductsMongoDao
import com.jeferro.products.products.infrastructure.adapters.mongo.mappers.ProductMongoMapper
import com.jeferro.products.shared.infrastructure.adapters.mongo.MongodbContainerCreator
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Import
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@DataMongoTest
@Import(value = [ProductsRepositoryMongo::class])
class ProductsRepositoryMongoIT {

    companion object {

        @Container
        @ServiceConnection
        val mongodbContainer = MongodbContainerCreator.create()
    }

    @Autowired
    private lateinit var productsMongoDao: ProductsMongoDao

    @Autowired
    private lateinit var productsRepositoryMongo: ProductsRepositoryMongo

    @Nested
    inner class SaveTests {

        @Test
        fun `should save new product`() = runTest {
            initializeDatabaseWith()

            val newProduct = oneProduct()
            productsRepositoryMongo.save(newProduct)

            val foundProduct = productsRepositoryMongo.findById(newProduct.id)
            assertEquals(newProduct, foundProduct)
        }

        @Test
        fun `should update product`() = runTest {
            val oneProduct = oneProduct()
            initializeDatabaseWith(oneProduct)

            val title = "new title"
            val description = "new description"
            oneProduct.update(title, description, oneProduct.createdBy)
            oneProduct.pullEvents()

            productsRepositoryMongo.save(oneProduct)

            val foundProduct = productsRepositoryMongo.findById(oneProduct.id)
            assertEquals(oneProduct, foundProduct)
        }
    }

    @Nested
    inner class FindByIdTests {

        @Test
        fun `should return data when product exists`() = runTest {
            val expected = oneProduct()
            initializeDatabaseWith(expected)

            val result = productsRepositoryMongo.findById(expected.id)

            assertEquals(expected, result)
        }

        @Test
        fun `should return null when product doesn't exist`() = runTest {
            initializeDatabaseWith()

            val productId = oneProductId()
            val result = productsRepositoryMongo.findById(productId)

            assertNull(result)
        }
    }

    @Nested
    inner class DeleteByIdTests {

        @Test
        fun `should delete product when it exists`() = runTest {
            val expected = oneProduct()
            initializeDatabaseWith(expected)

            productsRepositoryMongo.deleteById(expected.id)

            val foundProduct = productsRepositoryMongo.findById(expected.id)
            assertNull(foundProduct)
        }

        @Test
        fun `should do nothing when product doesn't exist`() = runTest {
            initializeDatabaseWith()

            val productId = oneProductId()
            productsRepositoryMongo.deleteById(productId)

            val foundProduct = productsRepositoryMongo.findById(productId)
            assertNull(foundProduct)
        }
    }

    @Nested
    inner class FindAllTests {

        @Test
        fun `should return data when product exist`() = runTest {
            val oneProduct = oneProduct()
            val twoProduct = twoProduct()
            initializeDatabaseWith(oneProduct, twoProduct)

            val result = productsRepositoryMongo.findAll()

            assertTrue(result.contains(oneProduct))
            assertTrue(result.contains(twoProduct))
        }

        @Test
        fun `should return empty list when product doesn't exist`() = runTest {
            initializeDatabaseWith()

            val result = productsRepositoryMongo.findAll()

            assertTrue(result.isEmpty)
        }
    }

    private suspend fun initializeDatabaseWith(vararg products: Product) {
        productsMongoDao.deleteAll()

        val dtos = products.toList()
        ProductMongoMapper.instance.toDTOList(dtos)
            .forEach { productDTO -> productsMongoDao.save(productDTO) }
    }
}