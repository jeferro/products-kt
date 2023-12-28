package com.jeferro.products.domain.shared.entities.metadata

import com.jeferro.products.domain.shared.entities.auth.UserIdMother.oneUserId
import com.jeferro.products.domain.shared.entities.auth.UserIdMother.twoUserId
import com.jeferro.products.domain.shared.entities.metadata.Metadata.Companion.metadataOf
import com.jeferro.products.domain.shared.entities.metadata.MetadataMother.oneMetadata
import com.jeferro.products.domain.shared.services.time.FakeTimeService.Companion.timeServiceFakesNow
import com.jeferro.products.domain.shared.services.time.FakeTimeService.Companion.timeServiceFakesOneMinuteLater
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class MetadataTest {

    @Nested
    inner class Constructor {
        @Test
        fun `should create metadata`() {
            val expectedMetadata = oneMetadata()

            val result = Metadata(
                expectedMetadata.createdAt,
                expectedMetadata.createdBy,
                expectedMetadata.updatedAt,
                expectedMetadata.updatedBy
            )

            assertEquals(expectedMetadata, result)
        }
    }

    @Nested
    inner class NamedConstructors {

        @Test
        fun `should create metadata of user id`() {
            val oneUserId = oneUserId()
            val now = timeServiceFakesOneMinuteLater()

            val result = metadataOf(oneUserId)

            assertEquals(now, result.createdAt)
            assertEquals(oneUserId, result.createdBy)
            assertEquals(now, result.updatedAt)
            assertEquals(oneUserId, result.updatedBy)
        }
    }

    @Nested
    inner class MarkAsModified {

        @Test
        fun `should mark as modified the metadata`() {
            val createdBy = oneUserId()
            val createdAt = timeServiceFakesNow()
            val metadata = metadataOf(createdBy)

            val updatedBy = twoUserId()
            val updatedAt = timeServiceFakesOneMinuteLater()
            metadata.markAsModifyBy(updatedBy)

            assertEquals(createdAt, metadata.createdAt)
            assertEquals(createdBy, metadata.createdBy)
            assertEquals(updatedAt, metadata.updatedAt)
            assertEquals(updatedBy, metadata.updatedBy)
        }
    }
}