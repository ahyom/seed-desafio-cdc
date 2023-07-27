package com.ahyom.cdc.controller

import com.ahyom.cdc.domain.mapper.LivroMapper
import com.ahyom.cdc.domain.request.LivroRequest
import com.ahyom.cdc.service.LivroService
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
@RequestMapping("livros")
class LivrosController @Autowired constructor(
    val livroService: LivroService,
    val livroMapper: LivroMapper,
) {

    @PostMapping
    fun createLivro(
        @Valid @RequestBody
        livroRequest: LivroRequest,
    ): ResponseEntity<LivroRequest> {
        val livro = livroMapper.toEntity(livroRequest)
        val livroCreated = livroService.createLivro(livro)
        return ResponseEntity.status(HttpStatus.CREATED).body(livroMapper.fromEntity(livroCreated))
    }

    @PutMapping("/{id-livro}")
    fun updateLivro(
        @Valid @RequestBody
        livroRequest: LivroRequest,
        @PathVariable("id-livro") idLivro: String,
    ): ResponseEntity<LivroRequest> {
        livroRequest.id = UUID.fromString(idLivro)
        val livro = livroMapper.toEntity(livroRequest)
        val livroCreated = livroService.updateLivro(livro)
        return ResponseEntity.status(HttpStatus.CREATED).body(livroMapper.fromEntity(livroCreated))
    }

    @GetMapping
    fun getLivros(): ResponseEntity<List<LivroRequest>> {
        val livros = livroService.getLivros()
        val livroRequestList = livros.map { livroMapper.fromEntity(it) }
        return ResponseEntity.ok(livroRequestList)
    }

    @GetMapping("/{id-livro}")
    fun getLivroById(@PathVariable("id-livro") idLivro: String): ResponseEntity<LivroRequest> {
        val livro = livroService.getLivroById(UUID.fromString(idLivro))
        return ResponseEntity.ok(livroMapper.fromEntity(livro))
    }

    @DeleteMapping("/{id-livro}")
    fun deleteLivro(@PathVariable("id-livro") idLivro: String): ResponseEntity<Unit> {
        livroService.deleteLivro(UUID.fromString(idLivro))
        return ResponseEntity.noContent().build()
    }
}
