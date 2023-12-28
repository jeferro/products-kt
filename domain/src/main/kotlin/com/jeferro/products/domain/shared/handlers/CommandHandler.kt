package com.jeferro.products.domain.shared.handlers

import com.jeferro.products.domain.shared.handlers.params.Params

abstract class CommandHandler<P : Params<R>, R>: BaseHandler<P, R>() {
}