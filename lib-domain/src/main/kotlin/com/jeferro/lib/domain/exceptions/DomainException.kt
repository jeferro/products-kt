package com.jeferro.lib.domain.exceptions

abstract class DomainException(
    message: String,
    cause: Exception?
) : RuntimeException(message, cause)