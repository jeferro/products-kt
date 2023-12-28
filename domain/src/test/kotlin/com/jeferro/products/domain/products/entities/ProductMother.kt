package com.jeferro.products.domain.products.entities

import com.jeferro.products.domain.products.entities.ProductIdMother.oneProductId
import com.jeferro.products.domain.products.entities.ProductIdMother.twoProductId
import com.jeferro.products.domain.shared.entities.auth.UserId
import com.jeferro.products.domain.shared.entities.metadata.Metadata.Companion.metadataOf
import com.jeferro.products.domain.shared.entities.metadata.MetadataMother.oneMetadata

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
        val metadata = metadataOf(createdBy)

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