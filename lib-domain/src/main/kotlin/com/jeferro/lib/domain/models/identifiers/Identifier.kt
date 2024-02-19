package com.jeferro.lib.domain.models.identifiers

import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.apache.commons.lang3.builder.ReflectionToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE

abstract class Identifier {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        if (other !is Identifier) {
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
        return ReflectionToStringBuilder.toString(this, SHORT_PREFIX_STYLE)
    }
}