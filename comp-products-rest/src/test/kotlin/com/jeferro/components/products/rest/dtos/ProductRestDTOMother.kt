package com.jeferro.components.products.rest.dtos

import com.jeferro.components.products.rest.dtos.MetadataRestDTOMother.oneMetadataRestDTO

object ProductRestDTOMother {

    fun oneProductRestDTO(): ProductRestDTO {
        return ProductRestDTO(
            "one-product-id",
            "title of the one product",
            "description of the one product",
            true,
            oneMetadataRestDTO()
        )
    }
}