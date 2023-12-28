package com.jeferro.products.domain.products.handlers.params

import com.jeferro.products.domain.products.entities.Products
import com.jeferro.products.domain.shared.entities.auth.Auth
import com.jeferro.products.domain.shared.handlers.params.Params

class ListProductsParams(
    auth: Auth
) : Params<Products>(auth)