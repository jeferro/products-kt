package com.jeferro.products.products.domain.handlers.operations

import com.jeferro.lib.domain.handlers.Operation
import com.jeferro.lib.domain.models.auth.Auth
import com.jeferro.products.products.domain.models.Product
import com.jeferro.products.products.domain.models.ProductId

class GetProduct(
    auth: Auth,
    val productId: ProductId
) : Operation<Product>(auth)