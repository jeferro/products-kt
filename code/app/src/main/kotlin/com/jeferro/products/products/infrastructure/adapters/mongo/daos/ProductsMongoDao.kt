package com.jeferro.products.products.infrastructure.adapters.mongo.daos

import com.jeferro.products.products.infrastructure.adapters.mongo.dtos.ProductMongoDTO
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductsMongoDao : CoroutineCrudRepository<ProductMongoDTO, String> {


}