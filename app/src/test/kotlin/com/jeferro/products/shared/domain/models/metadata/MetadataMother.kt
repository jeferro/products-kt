package com.jeferro.products.shared.domain.models.metadata

import com.jeferro.lib.domain.models.metadata.Metadata
import com.jeferro.lib.domain.time.TimeService
import com.jeferro.products.shared.domain.models.auth.UserIdMother.oneUserId
import java.time.Instant

object MetadataMother {

    fun oneMetadata(): Metadata {
        val userId = oneUserId();
        val now = TimeService.now()

        return Metadata(
            now,
            userId,
            now,
            userId
        )
    }
}