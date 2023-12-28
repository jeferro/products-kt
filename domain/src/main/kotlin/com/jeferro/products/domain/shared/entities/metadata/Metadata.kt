package com.jeferro.products.domain.shared.entities.metadata

import com.jeferro.products.domain.shared.entities.auth.UserId
import com.jeferro.products.domain.shared.entities.values.Value
import com.jeferro.products.domain.shared.utils.time.TimeService
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
        fun metadataOf(userId: UserId): Metadata {
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