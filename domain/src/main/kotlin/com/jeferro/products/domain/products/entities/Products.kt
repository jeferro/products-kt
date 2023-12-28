package com.jeferro.products.domain.products.entities

import com.jeferro.products.domain.shared.entities.entities.EntityCollection

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
    }
}