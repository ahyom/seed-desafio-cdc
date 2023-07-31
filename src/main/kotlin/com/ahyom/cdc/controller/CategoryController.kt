package com.ahyom.cdc.controller

import com.ahyom.cdc.domain.mapper.CategoryMapper
import com.ahyom.cdc.domain.request.CategoryRequest
import com.ahyom.cdc.service.CategoryService
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
@RequestMapping("categories")
class CategoryController @Autowired constructor(
    val categoryService: CategoryService,
    val categoryMapper: CategoryMapper,
) {

    @PostMapping
    fun createCategory(
        @Valid @RequestBody
        categoryRequest: CategoryRequest,
    ): ResponseEntity<CategoryRequest> {
        val category = categoryMapper.toEntity(categoryRequest)
        val categoryCreated = categoryService.createCategoria(category)
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryMapper.fromEntity(categoryCreated))
    }

    @PutMapping("/{category-id}")
    fun updateCategory(
        @PathVariable("category-id") idCategoria: String,
        @Valid @RequestBody
        categoryRequest: CategoryRequest,
    ): ResponseEntity<CategoryRequest> {
        categoryRequest.id = UUID.fromString(idCategoria)
        val category = categoryMapper.toEntity(categoryRequest)
        val categoryUpdated = categoryService.updateCategoria(category)
        return ResponseEntity.ok(categoryMapper.fromEntity(categoryUpdated))
    }

    @GetMapping
    fun getCategories(): ResponseEntity<List<CategoryRequest>> {
        val categories = categoryService.getCategorias()
        val categoryRequestList = categories.map { categoryMapper.fromEntity(it) }
        return ResponseEntity.ok(categoryRequestList)
    }

    @GetMapping("/{category-id}")
    fun getCategoryById(@PathVariable("category-id") idCategoria: String): ResponseEntity<CategoryRequest> {
        val category = categoryService.getCategoriaById(idCategoria)
        return ResponseEntity.ok(categoryMapper.fromEntity(category))
    }

    @DeleteMapping("/{category-id}")
    fun deleteCategory(@PathVariable("category-id") idCategoria: String): ResponseEntity<Unit> {
        categoryService.deleteCategoria(idCategoria)
        return ResponseEntity.noContent().build()
    }
}
