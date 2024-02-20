package com.jeferro.lib.application

import com.jeferro.lib.application.operations.Operation

abstract class CommandHandler<O : Operation<R>, R>: BaseHandler<O, R>()