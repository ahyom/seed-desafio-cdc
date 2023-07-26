package com.ahyom.cdc.domain.mapper

import com.ahyom.cdc.domain.entity.Livro
import com.ahyom.cdc.domain.request.LivroRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.UUID

@Component
class LivroMapper @Autowired constructor(
    val categoriaMapper: CategoriaMapper,
    val autorMapper: AutorMapper,
) : Mapper<LivroRequest, Livro> {
    override fun fromEntity(entity: Livro): LivroRequest {
        return LivroRequest(
            id = entity.id,
            title = entity.title,
            summary = entity.summary,
            tableOfContents = entity.tableOfContents,
            price = entity.price,
            pageNumbers = entity.pageNumbers,
            isbn = entity.isbn,
            publishDate = entity.publishDate,
            categoria = categoriaMapper.fromEntity(entity.categoria),
            autor = autorMapper.fromEntity(entity.autor),
            createdAt = entity.createdAt,
        )
    }

    override fun toEntity(domain: LivroRequest): Livro {
        if (domain.id == null) {
            domain.id = UUID.randomUUID()
        }

        if (domain.createdAt == null) {
            domain.createdAt = LocalDateTime.now()
        }

        return Livro(
            id = domain.id!!,
            title = domain.title,
            summary = domain.summary,
            tableOfContents = domain.tableOfContents,
            price = domain.price,
            pageNumbers = domain.pageNumbers,
            isbn = domain.isbn,
            publishDate = domain.publishDate,
            categoria = categoriaMapper.toEntity(domain.categoria),
            autor = autorMapper.toEntity(domain.autor),
            createdAt = domain.createdAt!!,
        )
    }
}
