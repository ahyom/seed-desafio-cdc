package com.ahyom.cdc.controller

import com.ahyom.cdc.domain.mapper.CategoriaMapper
import com.ahyom.cdc.domain.request.CategoriaRequest
import com.ahyom.cdc.service.CategoriaService
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
@RequestMapping("categoria")
class CategoriaController @Autowired constructor(
    val categoriaService: CategoriaService,
    val categoriaMapper: CategoriaMapper,
) {

    @PostMapping
    fun createCategoria(
        @Valid @RequestBody
        categoriaRequest: CategoriaRequest,
    ): ResponseEntity<CategoriaRequest> {
        val categoria = categoriaMapper.toEntity(categoriaRequest)
        val categoriaCreated = categoriaService.createCategoria(categoria)
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaMapper.fromEntity(categoriaCreated))
    }

    @PutMapping("/{categoria-id}")
    fun updateCategoria(
        @PathVariable("categoria-id") idCategoria: String,
        @Valid @RequestBody
        categoriaRequest: CategoriaRequest,
    ): ResponseEntity<CategoriaRequest> {
        categoriaRequest.id = UUID.fromString(idCategoria)
        val categoria = categoriaMapper.toEntity(categoriaRequest)
        val categoriaUpdated = categoriaService.updateCategoria(categoria)
        return ResponseEntity.ok(categoriaMapper.fromEntity(categoriaUpdated))
    }

    @GetMapping
    fun getCategorias(): ResponseEntity<List<CategoriaRequest>> {
        val categorias = categoriaService.getCategorias()
        val categoriaRequestList = categorias.map { categoriaMapper.fromEntity(it) }
        return ResponseEntity.ok(categoriaRequestList)
    }

    @GetMapping("/{categoria-id}")
    fun getCategoriaById(@PathVariable("categoria-id") idCategoria: String): ResponseEntity<CategoriaRequest> {
        val categoria = categoriaService.getCategoriaById(idCategoria)
        return ResponseEntity.ok(categoriaMapper.fromEntity(categoria))
    }

    @DeleteMapping("/{categoria-id}")
    fun deleteCategoria(@PathVariable("categoria-id") idCategoria: String): ResponseEntity<Unit> {
        categoriaService.deleteCategoria(idCategoria)
        return ResponseEntity.noContent().build()
    }
}
