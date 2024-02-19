package com.jeferro.lib.domain.exceptions

abstract class ConflictException(
    message: String,
    cause: Exception?
) : DomainException(message, cause)
