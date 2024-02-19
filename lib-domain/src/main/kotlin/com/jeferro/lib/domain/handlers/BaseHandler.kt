package com.jeferro.lib.domain.handlers

import com.jeferro.lib.domain.exceptions.DomainException
import com.jeferro.lib.domain.exceptions.InternalException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.Instant

sealed class BaseHandler<P : HandlerParams<R>, R> {

    protected val logger: Logger = LoggerFactory.getLogger(this::class.java)

    protected open val mandatoryRoles = emptyList<String>()

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

            throw InternalException.create(cause)
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