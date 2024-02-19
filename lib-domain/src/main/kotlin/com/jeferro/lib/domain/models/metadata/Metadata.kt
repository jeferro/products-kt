package com.jeferro.lib.domain.models.metadata

import com.jeferro.lib.domain.models.auth.UserId
import com.jeferro.lib.domain.models.values.Value
import com.jeferro.lib.domain.time.TimeService
import java.time.Instant

class Metadata(
    val createdAt: Instant,
    val createdBy: UserId,
    updatedAt: Instant,
    updatedBy: UserId
) : Value() {

    var updatedAt = updatedAt
        private set

    var updatedBy = updatedBy
        private set

    companion object {
        fun create(userId: UserId): Metadata {
            val now = TimeService.now()

            return Metadata(
                now,
                userId,
                now,
                userId
            )
        }
    }

    fun markAsModifyBy(userId: UserId) {
        this.updatedAt = TimeService.now()
        this.updatedBy = userId
    }
}