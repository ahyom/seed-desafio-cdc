package com.ahyom.cdc.service

import com.ahyom.cdc.domain.entity.Category
import com.ahyom.cdc.domain.exception.EntityAlreadyExistsException
import com.ahyom.cdc.domain.exception.NotFoundException
import com.ahyom.cdc.domain.repository.CategoryRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

private val logger = KotlinLogging.logger {}

@Service
class CategoryService @Autowired constructor(
    val categoryRepository: CategoryRepository,
) {

    fun createCategoria(category: Category): Category {
        logger.debug { "creating category..." }

        // should validate if category already exists
        validateIfCategoryWithNameAlreadyExists(category.name)

        categoryRepository.save(category)

        logger.debug { "category created with id ${category.id} and name ${category.name}" }

        return category
    }

    fun updateCategoria(category: Category): Category {
        logger.debug { "updating category ID: ${category.id} and name: ${category.name}" }
        categoryRepository.save(category)
        logger.debug { "updated category ID: ${category.id} and name: ${category.name}" }
        return category
    }

    fun getCategorias(): List<Category> {
        logger.debug { "getting categorys..." }
        return categoryRepository.findAll().toList()
    }

    fun getCategoriaById(idCategoria: String): Category {
        logger.debug { "getting category by id $idCategoria" }
        return categoryRepository
            .findById(UUID.fromString(idCategoria))
            .orElseThrow { NotFoundException("Categoria $idCategoria not found") }
    }

    fun getCategoriaByName(name: String): Category {
        logger.debug { "getting category by name $name" }
        return categoryRepository
            .findByName(name)
            .orElseThrow { NotFoundException("Categoria $name not found") }
    }

    fun deleteCategoria(idCategoria: String) {
        logger.debug { "deleting category by id $idCategoria" }
        categoryRepository.deleteById(UUID.fromString(idCategoria))
    }

    private fun validateIfCategoryWithNameAlreadyExists(name: String) {
        logger.debug { "validating if category $name already exists" }

        val category = categoryRepository.findByName(name)

        if (category.isPresent) {
            logger.error { "category $name already exists" }
            throw EntityAlreadyExistsException("Categoria $name already exists")
        }
    }
}
