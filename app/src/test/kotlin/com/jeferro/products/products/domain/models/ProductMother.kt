package com.jeferro.products.products.domain.models

import com.jeferro.lib.domain.models.auth.UserId
import com.jeferro.products.products.domain.models.ProductIdMother.oneProductId
import com.jeferro.products.products.domain.models.ProductIdMother.twoProductId
import com.jeferro.products.shared.domain.models.metadata.MetadataMother.oneMetadata

object ProductMother {

    fun oneProduct(): Product {
        val oneProductId = oneProductId()
        val metadata = oneMetadata()

        return Product(
            oneProductId,
            "title one product",
            "description one product",
            true,
            metadata
        )
    }

    fun oneProduct(enabled: Boolean): Product {
        val oneProductId = oneProductId()
        val metadata = oneMetadata()

        return Product(
            oneProductId,
            "title one product",
            "description one product",
            enabled,
            metadata
        )
    }

    fun oneProduct(createdBy: UserId): Product {
        val oneProductId = oneProductId()
        val metadata = oneMetadata(createdBy)

        return Product(
            oneProductId,
            "title one product",
            "description one product",
            true,
            metadata
        )
    }

    fun twoProduct(): Product {
        val twoProductId = twoProductId()
        val metadata = oneMetadata()

        return Product(
            twoProductId,
            "title two product",
            "description two product",
            true,
            metadata
        )
    }
}