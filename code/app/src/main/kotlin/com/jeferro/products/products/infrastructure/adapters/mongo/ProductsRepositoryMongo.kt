package com.jeferro.products.products.infrastructure.adapters.mongo

import com.jeferro.products.domain.products.entities.Products
import com.jeferro.products.products.domain.models.Product
import com.jeferro.products.products.domain.models.ProductId
import com.jeferro.products.products.domain.repositories.ProductsRepository
import com.jeferro.products.products.infrastructure.adapters.mongo.daos.ProductsMongoDao
import com.jeferro.products.products.infrastructure.adapters.mongo.mappers.ProductIdMongoMapper
import com.jeferro.products.products.infrastructure.adapters.mongo.mappers.ProductMongoMapper
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Repository

@Repository
class ProductsRepositoryMongo(
    private val productsMongoDao: ProductsMongoDao
) : ProductsRepository {
    private val productIdMongoMapper = ProductIdMongoMapper.instance

    private val productMongoMapper = ProductMongoMapper.instance


    override suspend fun save(product: Product) {
        val productDTO = productMongoMapper.toDTO(product)

        productsMongoDao.save(productDTO)
    }

    override suspend fun findById(productId: ProductId): Product? {
        val productIdDTO = productIdMongoMapper.toDTO(productId)

        val productDTO = productsMongoDao.findById(productIdDTO)
            ?: return null

        return productMongoMapper.toEntity(productDTO)
    }

    override suspend fun deleteById(productId: ProductId) {
        val productIdDTO = productIdMongoMapper.toDTO(productId)

        productsMongoDao.deleteById(productIdDTO)
    }

    override suspend fun findAll(): Products {
        val productDTOS = productsMongoDao.findAll().toList()

        return productMongoMapper.toProducts(productDTOS)
    }
}