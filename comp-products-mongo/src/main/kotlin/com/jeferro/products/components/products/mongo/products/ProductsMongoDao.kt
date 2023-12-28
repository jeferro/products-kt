package com.jeferro.products.components.products.mongo.products

import com.jeferro.products.components.products.mongo.products.dtos.ProductMongoDTO
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductsMongoDao : CoroutineCrudRepository<ProductMongoDTO, String> {


}