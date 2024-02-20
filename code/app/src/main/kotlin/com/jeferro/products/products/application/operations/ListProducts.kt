package com.jeferro.products.products.application.operations

import com.jeferro.lib.application.operations.Operation
import com.jeferro.lib.domain.models.auth.Auth
import com.jeferro.products.domain.products.entities.Products

class ListProducts(
    auth: Auth
) : Operation<Products>(auth)