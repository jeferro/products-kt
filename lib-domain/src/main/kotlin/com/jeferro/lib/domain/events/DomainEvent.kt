package com.jeferro.lib.domain.events

import com.jeferro.lib.domain.models.auth.UserId
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.apache.commons.lang3.builder.ReflectionToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import java.time.Instant

abstract class DomainEvent(
    val occurredBy: UserId,
    val occurredOn: Instant
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        if (other !is DomainEvent) {
            return false
        }

        return EqualsBuilder.reflectionEquals(
            this,
            other
        )
    }

    override fun hashCode(): Int {
        return HashCodeBuilder.reflectionHashCode(this)
    }

    override fun toString(): String {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE)
    }
}