package com.jeferro.products.products.domain.handlers.operations

import com.jeferro.lib.domain.handlers.Operation
import com.jeferro.lib.domain.models.auth.Auth
import com.jeferro.products.domain.products.entities.Products

class ListProducts(
    auth: Auth
) : Operation<Products>(auth)