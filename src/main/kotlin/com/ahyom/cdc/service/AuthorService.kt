package com.ahyom.cdc.service

import com.ahyom.cdc.domain.entity.Author
import com.ahyom.cdc.domain.exception.EntityAlreadyExistsException
import com.ahyom.cdc.domain.exception.NotFoundException
import com.ahyom.cdc.domain.repository.AuthorRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

private val logger = KotlinLogging.logger {}

@Service
class AuthorService @Autowired constructor(
    val authorRepository: AuthorRepository,
) {
    fun createAuthor(author: Author): Author {
        logger.debug { "creating author..." }

        // should validate if email already exists
        validateIfEmailAlreadyExists(author.email)

        // will save author and log information
        authorRepository.save(author)

        logger.debug { "author created with id ${author.id}" }
        return author
    }

    fun updateAuthor(author: Author, authorId: UUID): Author {
        logger.debug { "updating author ID: $authorId and name: ${author.name}" }
        authorRepository.save(author)
        logger.debug { "updated author ID: $authorId and name: ${author.name}" }
        return author
    }

    fun getAuthors(): List<Author> {
        logger.debug { "getting authors..." }
        return authorRepository.findAll().toList()
    }

    fun getAuthorById(authorId: UUID): Author {
        logger.debug { "getting author by id $authorId" }
        return authorRepository
            .findById(authorId)
            .orElseThrow { NotFoundException("Author $authorId not found") }
    }

    fun deleteAuthor(authorId: UUID) {
        logger.debug { "deleting author by id $authorId" }
        authorRepository.deleteById(authorId)
    }

    private fun validateIfEmailAlreadyExists(email: String) {
        logger.debug { "validating if email $email already exists" }

        val author = authorRepository.findByEmail(email)

        if (author.isPresent) {
            logger.error { "email $email already exists" }
            throw EntityAlreadyExistsException("Author com o email $email já existente")
        }
    }
}
