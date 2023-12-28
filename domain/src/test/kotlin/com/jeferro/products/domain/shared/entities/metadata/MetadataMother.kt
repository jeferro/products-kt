package com.jeferro.products.domain.shared.entities.metadata

import com.jeferro.products.domain.shared.utils.time.TimeService
import com.jeferro.products.domain.shared.entities.auth.UserIdMother.oneUserId

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