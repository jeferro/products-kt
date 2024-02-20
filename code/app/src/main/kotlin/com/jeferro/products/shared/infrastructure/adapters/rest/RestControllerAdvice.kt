package com.jeferro.products.shared.infrastructure.adapters.rest

import com.jeferro.lib.domain.exceptions.*
import com.jeferro.products.components.products.rest.dtos.ErrorRestDTO
import org.slf4j.LoggerFactory
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

    private val logger = LoggerFactory.getLogger(this::class.java)

    @ResponseBody
    @ExceptionHandler(
        value = [
            IllegalArgumentException::class,
            ValueValidationException::class,
            ServerWebInputException::class,
            MissingServletRequestParameterException::class
        ]
    )
    fun handleValueValidationException(cause: Exception): ResponseEntity<ErrorRestDTO> {
        return createErrorResponse(
            ErrorRestDTO.Code.VALUE_VALIDATION,
            HttpStatus.BAD_REQUEST,
            cause
        )
    }

    @ResponseBody
    @ExceptionHandler(
        value = [
            NotFoundException::class,
            NoResourceFoundException::class
        ]
    )
    fun handleNotFoundException(cause: Exception): ResponseEntity<ErrorRestDTO> {
        return createErrorResponse(
            ErrorRestDTO.Code.NOT_FOUND,
            HttpStatus.NOT_FOUND,
            cause
        )
    }

    @ResponseBody
    @ExceptionHandler(
        value = [
            UnauthorizedException::class
        ]
    )
    fun handleUnauthorizedException(cause: UnauthorizedException): ResponseEntity<ErrorRestDTO> {
        return createErrorResponse(
            ErrorRestDTO.Code.UNAUTHORIZED,
            HttpStatus.UNAUTHORIZED,
            cause
        )
    }

    @ResponseBody
    @ExceptionHandler(
        value = [
            ForbiddenException::class
        ]
    )
    fun handleForbiddenException(cause: ForbiddenException): ResponseEntity<ErrorRestDTO> {
        return createErrorResponse(
            ErrorRestDTO.Code.FORBIDDEN,
            HttpStatus.FORBIDDEN,
            cause
        )
    }

    @ResponseBody
    @ExceptionHandler(
        value = [
            InternalException::class
        ]
    )
    fun handleInternalException(cause: InternalException): ResponseEntity<ErrorRestDTO> {
        return createErrorResponse(
            ErrorRestDTO.Code.INTERNAL_ERROR,
            HttpStatus.INTERNAL_SERVER_ERROR,
            cause
        )
    }

    @ResponseBody
    @ExceptionHandler(value = [Exception::class])
    fun handleException(cause: Exception): ResponseEntity<ErrorRestDTO> {
        logger.error("Catch an unknown exception", cause)

        return createErrorResponse(
            ErrorRestDTO.Code.INTERNAL_ERROR,
            HttpStatus.INTERNAL_SERVER_ERROR,
            cause
        )
    }

    private fun createErrorResponse(
        code: ErrorRestDTO.Code,
        status: HttpStatus,
        cause: Exception
    ): ResponseEntity<ErrorRestDTO> {
        val errorDTO = ErrorRestDTO(
            code,
            cause.message ?: ""
        )

        return ResponseEntity
            .status(status)
            .body(errorDTO)
    }
}
