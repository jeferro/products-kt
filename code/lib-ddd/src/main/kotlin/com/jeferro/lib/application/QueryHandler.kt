package com.jeferro.lib.application

import com.jeferro.lib.application.operations.Operation

abstract class QueryHandler<O : Operation<R>, R>: BaseHandler<O, R>() {
}