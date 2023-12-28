package com.jeferro.products.domain.shared.exceptions

abstract class NotFoundException(
    message: String
) : DomainException(message, null)