package com.ahyom.cdc.domain.mapper

import com.ahyom.cdc.domain.entity.Category
import com.ahyom.cdc.domain.request.CategoryRequest
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.UUID

private val logger = KotlinLogging.logger {}

@Component
class CategoryMapper : Mapper<CategoryRequest, Category> {
    override fun fromEntity(entity: Category): CategoryRequest {
        return CategoryRequest(
            id = entity.id,
            name = entity.name,
            createdAt = entity.createdAt,
        )
    }

    override fun toEntity(domain: CategoryRequest): Category {
        if (domain.id == null) {
            logger.debug { "generating id for categoria..." }
            domain.id = UUID.randomUUID()
        }

        if (domain.createdAt == null) {
            domain.createdAt = LocalDateTime.now()
        }

        return Category(
            id = domain.id!!,
            name = domain.name,
            createdAt = domain.createdAt!!,
        )
    }
}