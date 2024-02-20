package com.jeferro.lib.domain.events

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.Instant

abstract class BaseEventHandler<E : DomainEvent, R> {

    protected val logger: Logger = LoggerFactory.getLogger(this::class.java)

    suspend fun execute(event: E) {
        val startAt = Instant.now()

        try {
            val result = consume(event)

            logSuccessExecution(startAt, event, result)
        } catch (cause: Exception) {
            logErrorExecution(startAt, event, cause)
        }
    }

    protected abstract suspend fun consume(event: E): R

    private fun logSuccessExecution(
        startAt: Instant,
        event: E,
        result: R
    ) {
        val duration = calculateDuration(startAt)

        logger.info("$duration \n\t $event \n\t $result\n")
    }

    private fun logErrorExecution(
        startAt: Instant,
        event: E,
        cause: Exception
    ) {
        val duration = calculateDuration(startAt)

        logger.error("$duration \n\t $event", cause)
    }

    private fun calculateDuration(startAt: Instant): Duration {
        val endAt = Instant.now()

        return Duration.between(startAt, endAt)
    }
}