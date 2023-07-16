package com.ahyom.cdc.controller

import com.ahyom.cdc.domain.mapper.AutorMapper
import com.ahyom.cdc.domain.request.AutorRequest
import com.ahyom.cdc.service.AutorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("autor")
class AutorController @Autowired constructor(
    val autorService: AutorService,
    val autorMapper: AutorMapper,
) {

    @PostMapping
    fun createAutor(@RequestBody autorRequest: AutorRequest): ResponseEntity<AutorRequest> {
        val autor = autorMapper.toEntity(autorRequest)

        val autorCreated = autorService.createAutor(autor)

        return ResponseEntity.ok(autorMapper.fromEntity(autorCreated))
    }

    @GetMapping
    fun getAutors(): ResponseEntity<List<AutorRequest>> {
        val autors = autorService.getAutors()
        val autorRequestList = autors.map { autorMapper.fromEntity(it) }

        return ResponseEntity.ok(autorRequestList)
    }

    @GetMapping("/{autor-id}")
    fun getAutorById(@PathVariable("autor-id") autorId: String): ResponseEntity<AutorRequest> {
        val autor = autorService.getAutorById(UUID.fromString(autorId))
        val autorRequest = autorMapper.fromEntity(autor)
        return ResponseEntity.ok(autorRequest)
    }

    @DeleteMapping("/{autor-id}")
    fun deleteAutor(@PathVariable("autor-id") autorId: String): ResponseEntity<Unit> {
        autorService.deleteAutor(UUID.fromString(autorId))
        return ResponseEntity.ok().build()
    }
}
