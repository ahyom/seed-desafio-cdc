package com.ahyom.cdc.domain.mapper

import com.ahyom.cdc.domain.entity.Author
import com.ahyom.cdc.domain.request.AuthorRequest
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.UUID

private val logger = KotlinLogging.logger {}

@Component
class AuthorMapper : Mapper<AuthorRequest, Author> {
    override fun fromEntity(entity: Author): AuthorRequest {
        return AuthorRequest(
            id = entity.id,
            name = entity.name,
            email = entity.email,
            description = entity.description,
            createdAt = entity.createdAt,
        )
    }

    override fun toEntity(domain: AuthorRequest): Author {
        if (domain.id == null) {
            logger.debug { "generating id for autor..." }
            domain.id = UUID.randomUUID()
        }

        if (domain.createdAt == null) {
            domain.createdAt = LocalDateTime.now()
        }

        return Author(
            id = domain.id!!,
            name = domain.name,
            email = domain.email,
            description = domain.description,
            createdAt = domain.createdAt!!,
        )
    }
}
