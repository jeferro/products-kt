package com.jeferro.lib.domain.exceptions

open class NotFoundException(
    message: String
) : DomainException(message, null)