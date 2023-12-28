package com.jeferro.products.infrastructure.product_details.handlers

import com.jeferro.products.domain.products.events.ProductUpserted
import com.jeferro.products.domain.shared.events.handlers.BaseEventHandler
import com.jeferro.products.domain.shared.events.handlers.EventHandler

@EventHandler
class UpdateProductDetailInfo : BaseEventHandler<ProductUpserted, Void?>() {

    override suspend fun consume(event: ProductUpserted): Void? {
        return null
    }
}