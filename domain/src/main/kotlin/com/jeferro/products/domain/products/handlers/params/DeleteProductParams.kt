package com.jeferro.products.domain.products.handlers.params

import com.jeferro.products.domain.products.entities.Product
import com.jeferro.products.domain.products.entities.ProductId
import com.jeferro.products.domain.shared.entities.auth.Auth
import com.jeferro.products.domain.shared.handlers.params.Params

class DeleteProductParams(
    auth: Auth,
    val productId: ProductId
) : Params<Product>(auth)