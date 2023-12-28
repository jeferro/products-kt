package com.jeferro.products.components.products.mongo.poduct_reviews

import com.jeferro.products.components.products.mongo.poduct_reviews.dtos.ProductReviewMongoDTO
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductReviewsMongoDao : CoroutineCrudRepository<ProductReviewMongoDTO, String> {


}