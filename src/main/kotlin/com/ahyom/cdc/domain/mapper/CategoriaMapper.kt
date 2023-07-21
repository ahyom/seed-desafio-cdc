package com.ahyom.cdc.domain.mapper

import com.ahyom.cdc.domain.entity.Categoria
import com.ahyom.cdc.domain.request.CategoriaRequest
import mu.KotlinLogging
import java.time.LocalDateTime
import java.util.UUID

private val logger = KotlinLogging.logger {}

class CategoriaMapper : Mapper<CategoriaRequest, Categoria> {
    override fun fromEntity(entity: Categoria): CategoriaRequest {
        return CategoriaRequest(
            id = entity.id,
            name = entity.name,
            createdAt = entity.createdAt,
        )
    }

    override fun toEntity(domain: CategoriaRequest): Categoria {
        if (domain.id == null) {
            logger.debug { "generating id for categoria..." }
            domain.id = UUID.randomUUID()
        }

        if (domain.createdAt == null) {
            domain.createdAt = LocalDateTime.now()
        }

        return Categoria(
            id = domain.id!!,
            name = domain.name,
            createdAt = domain.createdAt!!,
        )
    }
}