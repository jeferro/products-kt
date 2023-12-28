package com.jeferro.products.components.products.mongo.product_reviews

import com.jeferro.products.components.products.mongo.shared.InMemoryMongoDao
import com.jeferro.products.components.products.mongo.poduct_reviews.ProductReviewsMongoDao
import com.jeferro.products.components.products.mongo.poduct_reviews.dtos.ProductReviewMongoDTO

class ProductReviewsInMemoryMongoDao : InMemoryMongoDao<ProductReviewMongoDTO>(), ProductReviewsMongoDao {

}