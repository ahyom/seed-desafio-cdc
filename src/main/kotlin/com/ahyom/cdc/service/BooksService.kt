package com.ahyom.cdc.service

import com.ahyom.cdc.domain.entity.Books
import com.ahyom.cdc.domain.exception.EntityAlreadyExistsException
import com.ahyom.cdc.domain.exception.NotFoundException
import com.ahyom.cdc.domain.repository.BooksRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

private val logger = KotlinLogging.logger {}

@Service
class BooksService @Autowired constructor(
    val booksRepository: BooksRepository,
) {

    fun createBook(books: Books): Books {
        logger.debug { "creating livro..." }

        // should validate if livro with ISBN already exists
        validateIfISBNAlreadyExists(books.isbn)

        booksRepository.save(books)

        logger.debug { "livro created with id ${books.id} and isbn ${books.isbn}" }

        return books
    }

    fun updateBook(books: Books): Books {
        logger.debug { "updating livro ID: ${books.id} and isbn: ${books.isbn}" }

        booksRepository.save(books)

        logger.debug { "updated livro ID: ${books.id} and isbn: ${books.isbn}" }

        return books
    }

    fun getBooks(): List<Books> {
        logger.debug { "getting livros..." }
        return booksRepository.findAll().toList()
    }

    fun getBookById(idBook: UUID): Books {
        logger.debug { "getting livro by id $idBook" }
        return booksRepository
            .findById(idBook)
            .orElseThrow { NotFoundException("Book $idBook not found") }
    }

    fun deleteBook(idBook: UUID) {
        logger.debug { "deleting livro by id $idBook" }
        booksRepository.deleteById(idBook)
    }

    fun validateIfISBNAlreadyExists(isbn: String) {
        logger.debug { "validating if livro with isbn $isbn already exists" }

        val livros = booksRepository.findByIsbn(isbn)

        if (livros.isPresent) {
            logger.error { "livro with isbn $isbn already exists" }
            throw EntityAlreadyExistsException("Book with isbn $isbn already exists")
        }
    }
}
