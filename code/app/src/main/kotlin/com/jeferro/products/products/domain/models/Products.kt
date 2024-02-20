package com.jeferro.products.domain.products.entities

import com.jeferro.lib.domain.models.entities.EntityCollection
import com.jeferro.products.products.domain.models.Product
import com.jeferro.products.products.domain.models.ProductId

class
Products(
    entities: List<Product>
) : EntityCollection<ProductId, Product>(entities) {

    companion object {
        fun of(vararg products: Product): Products {
            return Products(products.toList())
        }

        fun of(products: Iterable<Product>): Products {
            return Products(products.toList())
        }

        fun empty(): Products {
            return Products(emptyList())
        }
    }
}