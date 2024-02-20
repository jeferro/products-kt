package com.jeferro.products.products.application.operations

import com.jeferro.lib.application.operations.Operation
import com.jeferro.lib.domain.models.auth.Auth
import com.jeferro.products.products.domain.models.Product
import com.jeferro.products.products.domain.models.ProductId

class ChangeProductActivation(
    auth: Auth,
    val productId: ProductId,
    val isEnabled: Boolean
) : Operation<Product>(auth)