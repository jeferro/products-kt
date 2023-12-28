package com.jeferro.products.domain.products.handlers.params

import com.jeferro.products.domain.products.entities.Product
import com.jeferro.products.domain.products.entities.ProductId
import com.jeferro.products.domain.shared.handlers.params.Params
import com.jeferro.products.domain.shared.entities.auth.Auth

class UpsertProductParams(
    auth: Auth,
    val productId: ProductId,
    val title: String,
    val description: String
) : Params<Product>(auth)