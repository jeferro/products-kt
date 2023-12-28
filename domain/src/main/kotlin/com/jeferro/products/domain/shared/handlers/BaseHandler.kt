package com.jeferro.products.domain.shared.handlers

import com.jeferro.products.domain.shared.exceptions.DomainException
import com.jeferro.products.domain.shared.exceptions.InternalException.Companion.internalExceptionOf
import com.jeferro.products.domain.shared.handlers.params.Params
import com.jeferro.products.domain.shared.utils.logger.Logger.Companion.loggerOf
import java.time.Duration
import java.time.Instant

sealed class BaseHandler<P : Params<R>, R> {

    protected open val mandatoryRoles = emptyList<String>()

    protected val logger = loggerOf(this::class)

    suspend fun execute(params: P): R {
        val auth = params.auth

        val startAt = Instant.now()

        try {
            auth.ensurePermissions(mandatoryRoles)

            val result = handle(params)

            if (this is CommandHandler) {
                logSuccessExecution(startAt, params, result)
            }

            return result
        } catch (cause: Exception) {
            logErrorExecution(startAt, params, cause)

            if (cause is DomainException) {
                throw cause
            }

            throw internalExceptionOf(cause)
        }
    }

    protected abstract suspend fun handle(params: P): R

    private fun logSuccessExecution(
        startAt: Instant,
        params: P,
        result: R
    ) {
        val duration = calculateDuration(startAt)

        logger.info("$duration \n\t $params \n\t $result\n")
    }

    private fun logErrorExecution(
        startAt: Instant,
        params: P,
        cause: Exception
    ) {
        val duration = calculateDuration(startAt)

        logger.error("$duration \n\t $params", cause)
    }

    private fun calculateDuration(startAt: Instant): Duration {
        val endAt = Instant.now()

        return Duration.between(startAt, endAt)
    }
}