package com.jeferro.lib.infrastructure.shared.adapters.rest.mappers

import com.jeferro.lib.infrastructure.shared.adapters.mappers.BidireccionalMapper
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

@Mapper
abstract class InstantRestMapper : BidireccionalMapper<Instant, OffsetDateTime> {

    companion object {
        val instance = Mappers.getMapper(InstantRestMapper::class.java)
    }


    override fun toDTO(entity: Instant): OffsetDateTime {
        return Optional.ofNullable(entity)
            .map { instant ->
                instant.atOffset(ZoneOffset.UTC)
            }
            .orElse(null)
    }

    override fun toEntity(dto: OffsetDateTime): Instant {
        return Optional.ofNullable(dto)
            .map { obj: OffsetDateTime -> obj.toInstant() }
            .orElse(null)
    }
}