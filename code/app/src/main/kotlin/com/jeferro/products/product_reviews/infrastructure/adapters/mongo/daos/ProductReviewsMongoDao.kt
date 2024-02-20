package com.jeferro.products.product_reviews.infrastructure.adapters.mongo.daos

import com.jeferro.products.product_reviews.infrastructure.adapters.mongo.dtos.ProductReviewMongoDTO
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductReviewsMongoDao : CoroutineCrudRepository<ProductReviewMongoDTO, String> {


}