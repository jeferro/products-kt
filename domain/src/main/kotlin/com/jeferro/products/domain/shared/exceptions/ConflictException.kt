package com.jeferro.products.domain.shared.exceptions

abstract class ConflictException(
    message: String,
    cause: Exception?
) : DomainException(message, cause) {
}
