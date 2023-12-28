package com.jeferro.products.domain.shared.exceptions

abstract class DomainException(
    message: String,
    cause: Exception?
) : Exception(message, cause)