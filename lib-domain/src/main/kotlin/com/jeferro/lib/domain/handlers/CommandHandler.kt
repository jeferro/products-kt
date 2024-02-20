package com.jeferro.lib.domain.handlers

abstract class CommandHandler<O : Operation<R>, R>: BaseHandler<O, R>()