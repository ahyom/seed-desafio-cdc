package com.ahyom.cdc.controller

import com.ahyom.cdc.domain.mapper.AuthorMapper
import com.ahyom.cdc.domain.request.AuthorRequest
import com.ahyom.cdc.service.AuthorService
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
@RequestMapping("authors")
class AuthorController @Autowired constructor(
    val authorService: AuthorService,
    val authorMapper: AuthorMapper,
) {

    @PostMapping
    fun createAuthor(
        @Valid @RequestBody
        authorRequest: AuthorRequest,
    ): ResponseEntity<AuthorRequest> {
        val author = authorMapper.toEntity(authorRequest)

        val authorCreated = authorService.createAuthor(author)

        return ResponseEntity.status(HttpStatus.CREATED).body(authorMapper.fromEntity(authorCreated))
    }

    @PutMapping("/{author-id}")
    fun updateAuthor(
        @PathVariable("author-id") authorId: String,
        @Valid @RequestBody
        authorRequest: AuthorRequest,
    ): ResponseEntity<AuthorRequest> {
        // ID is not a mandatory field in the request body so ill set it here to guarantee the same ID is used
        authorRequest.id = UUID.fromString(authorId)

        val author = authorMapper.toEntity(authorRequest)

        val authorUpdated = authorService.updateAuthor(author, UUID.fromString(authorId))

        return ResponseEntity.ok(authorMapper.fromEntity(authorUpdated))
    }

    @GetMapping
    fun getAuthors(): ResponseEntity<List<AuthorRequest>> {
        val authors = authorService.getAuthors()
        val authorRequestList = authors.map { authorMapper.fromEntity(it) }

        return ResponseEntity.ok(authorRequestList)
    }

    @GetMapping("/{author-id}")
    fun getAuthorById(@PathVariable("author-id") authorId: String): ResponseEntity<AuthorRequest> {
        val author = authorService.getAuthorById(UUID.fromString(authorId))
        val authorRequest = authorMapper.fromEntity(author)
        return ResponseEntity.ok(authorRequest)
    }

    @DeleteMapping("/{author-id}")
    fun deleteAuthor(@PathVariable("author-id") authorId: String): ResponseEntity<Unit> {
        authorService.deleteAuthor(UUID.fromString(authorId))
        return ResponseEntity.noContent().build()
    }
}
