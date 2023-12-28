package com.jeferro.products.components.products.mongo.products

import com.jeferro.products.components.products.mongo.products.dtos.ProductMongoDTO
import com.jeferro.products.components.products.mongo.shared.InMemoryMongoDao

class ProductsInMemoryMongoDao : InMemoryMongoDao<ProductMongoDTO>(), ProductsMongoDao {

}