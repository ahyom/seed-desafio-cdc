package com.ahyom.cdc.model.mapper

import com.ahyom.cdc.model.entity.Autor
import com.ahyom.cdc.model.request.AutorRequest
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.UUID

private val logger = KotlinLogging.logger {}

@Component
class AutorMapper : Mapper<AutorRequest, Autor> {
    override fun fromEntity(entity: Autor): AutorRequest {
        return AutorRequest(
            id = entity.id,
            name = entity.name,
            email = entity.email,
            description = entity.description,
            createdAt = entity.createdAt,
        )
    }

    override fun toEntity(domain: AutorRequest): Autor {
        if (domain.id == null) {
            logger.debug { "generating id for autor..." }
            domain.id = UUID.randomUUID()
        }

        if (domain.createdAt == null) {
            domain.createdAt = LocalDateTime.now()
        }

        return Autor(
            id = domain.id!!,
            name = domain.name,
            email = domain.email,
            description = domain.description,
            createdAt = domain.createdAt!!,
        )
    }
}
