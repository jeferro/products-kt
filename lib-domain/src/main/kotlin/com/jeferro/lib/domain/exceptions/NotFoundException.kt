package com.jeferro.lib.domain.exceptions

class NotFoundException(
    message: String
) : DomainException(message, null)