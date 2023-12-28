package com.jeferro.products.infrastructure.shared.components.rest

import com.jeferro.components.products.rest.dtos.ErrorRestDTO
import com.jeferro.components.products.rest.dtos.ErrorRestDTO.Code
import com.jeferro.products.domain.shared.exceptions.*
import com.jeferro.products.domain.shared.utils.logger.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ServerWebInputException
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
internal class RestControllerAdvice {

    val logger = Logger.loggerOf(this::class)

    @ResponseBody
    @ExceptionHandler(value = [IllegalArgumentException::class])
    fun handleIllegalArgument(cause: IllegalArgumentException): ResponseEntity<ErrorRestDTO> {
        return createErrorResponse(
            Code.VALUE_VALIDATION,
            HttpStatus.BAD_REQUEST,
            cause
        )
    }

    @ResponseBody
    @ExceptionHandler(value = [Exception::class])
    fun handleException(cause: Exception?): ResponseEntity<ErrorRestDTO> {
        if (cause is InternalException) {
            return createErrorResponse(
                Code.INTERNAL_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR,
                cause
            )
        }

        if (cause is ValueValidationException
            || cause is ServerWebInputException
            || cause is MissingServletRequestParameterException
        ) {
            return createErrorResponse(
                Code.VALUE_VALIDATION,
                HttpStatus.BAD_REQUEST,
                cause
            )
        }

        if (cause is ForbiddenException) {
            return createErrorResponse(
                Code.FORBIDDEN,
                HttpStatus.FORBIDDEN,
                cause
            )
        }

        if (cause is UnauthorizedException) {
            return createErrorResponse(
                Code.UNAUTHORIZED,
                HttpStatus.UNAUTHORIZED,
                cause
            )
        }

        if (cause is NotFoundException
            || cause is NoResourceFoundException) {
            return createErrorResponse(
                Code.NOT_FOUND,
                HttpStatus.NOT_FOUND,
                cause
            )
        }

        logger.error("Catch an unknown exception", cause)

        return createErrorResponse(
            Code.INTERNAL_ERROR,
            HttpStatus.INTERNAL_SERVER_ERROR,
            cause
        )
    }

    private fun createErrorResponse(
        code: Code,
        status: HttpStatus,
        cause: Exception?
    ): ResponseEntity<ErrorRestDTO> {
        val errorDTO = ErrorRestDTO(
            code,
            cause?.message ?: ""
        )

        return ResponseEntity
            .status(status)
            .body(errorDTO)
    }
}
