package com.jeferro.lib.domain.handlers

import com.jeferro.lib.domain.exceptions.DomainException
import com.jeferro.lib.domain.exceptions.InternalException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.Instant

sealed class BaseHandler<O : Operation<R>, R> {

    protected val logger: Logger = LoggerFactory.getLogger(this::class.java)

    protected open val mandatoryRoles = emptyList<String>()

    suspend fun execute(operation: O): R {
        val auth = operation.auth

        val startAt = Instant.now()

        try {
            auth.ensurePermissions(mandatoryRoles)

            val result = handle(operation)

            if (this is CommandHandler) {
                logSuccessExecution(startAt, operation, result)
            }

            return result
        } catch (cause: Exception) {
            logErrorExecution(startAt, operation, cause)

            if (cause is DomainException) {
                throw cause
            }

            throw InternalException.create(cause)
        }
    }

    protected abstract suspend fun handle(operation: O): R

    private fun logSuccessExecution(
        startAt: Instant,
        operation: O,
        result: R
    ) {
        val duration = calculateDuration(startAt)

        logger.info("$duration \n\t $operation \n\t $result\n")
    }

    private fun logErrorExecution(
        startAt: Instant,
        operation: O,
        cause: Exception
    ) {
        val duration = calculateDuration(startAt)

        logger.error("$duration \n\t $operation", cause)
    }

    private fun calculateDuration(startAt: Instant): Duration {
        val endAt = Instant.now()

        return Duration.between(startAt, endAt)
    }
}