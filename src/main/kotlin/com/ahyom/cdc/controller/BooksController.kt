package com.ahyom.cdc.controller

import com.ahyom.cdc.domain.mapper.BookMapper
import com.ahyom.cdc.domain.request.BookRequest
import com.ahyom.cdc.service.BooksService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("books")
class BooksController @Autowired constructor(
    val booksService: BooksService,
    val bookMapper: BookMapper,
) {

    @PostMapping
    fun createBook(
        @RequestBody
        bookRequest: BookRequest,
    ): ResponseEntity<BookRequest> {
        val book = bookMapper.toEntity(bookRequest)
        val bookCreated = booksService.createBook(book)
        return ResponseEntity.status(HttpStatus.CREATED).body(bookMapper.fromEntity(bookCreated))
    }

    @PutMapping("/{id-book}")
    fun updateBook(
        @Valid @RequestBody
        bookRequest: BookRequest,
        @PathVariable("id-book") idBook: String,
    ): ResponseEntity<BookRequest> {
        bookRequest.id = UUID.fromString(idBook)
        val book = bookMapper.toEntity(bookRequest)
        val bookCreated = booksService.updateBook(book)
        return ResponseEntity.status(HttpStatus.OK).body(bookMapper.fromEntity(bookCreated))
    }

    @GetMapping
    fun getBooks(): ResponseEntity<List<BookRequest>> {
        val books = booksService.getBooks()
        val bookRequestList = books.map { bookMapper.fromEntity(it) }
        return ResponseEntity.ok(bookRequestList)
    }

    @GetMapping("/{id-book}")
    fun getBookById(@PathVariable("id-book") idBook: String): ResponseEntity<BookRequest> {
        val book = booksService.getBookById(UUID.fromString(idBook))
        return ResponseEntity.ok(bookMapper.fromEntity(book))
    }

    @DeleteMapping("/{id-book}")
    fun deleteBook(@PathVariable("id-book") idBook: String): ResponseEntity<Unit> {
        booksService.deleteBook(UUID.fromString(idBook))
        return ResponseEntity.noContent().build()
    }
}
