package com.ahyom.cdc.domain.mapper

import com.ahyom.cdc.domain.entity.Books
import com.ahyom.cdc.domain.request.BookRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.UUID

@Component
class BookMapper @Autowired constructor(
    val categoryMapper: CategoryMapper,
    val authorMapper: AuthorMapper,
) : Mapper<BookRequest, Books> {
    override fun fromEntity(entity: Books): BookRequest {
        return BookRequest(
            id = entity.id,
            title = entity.title,
            summary = entity.summary,
            tableOfContents = entity.tableOfContents,
            price = entity.price,
            pageNumbers = entity.pageNumbers,
            isbn = entity.isbn,
            publishDate = entity.publishDate,
            category = categoryMapper.fromEntity(entity.category),
            author = authorMapper.fromEntity(entity.author),
            createdAt = entity.createdAt,
        )
    }

    override fun toEntity(domain: BookRequest): Books {
        domain.validate().let {
            if (it.isNotEmpty()) {
                throw IllegalArgumentException(it.toString())
            }
        }

        if (domain.id == null) {
            domain.id = UUID.randomUUID()
        }

        if (domain.createdAt == null) {
            domain.createdAt = LocalDateTime.now()
        }

        return Books(
            id = domain.id!!,
            title = domain.title,
            summary = domain.summary,
            tableOfContents = domain.tableOfContents,
            price = domain.price,
            pageNumbers = domain.pageNumbers,
            isbn = domain.isbn,
            publishDate = domain.publishDate,
            category = categoryMapper.toEntity(domain.category),
            author = authorMapper.toEntity(domain.author),
            createdAt = domain.createdAt!!,
        )
    }
}
