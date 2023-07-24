package com.ahyom.cdc.service

import com.ahyom.cdc.domain.entity.Categoria
import com.ahyom.cdc.domain.exception.EntityAlreadyExistsException
import com.ahyom.cdc.domain.exception.NotFoundException
import com.ahyom.cdc.domain.repository.CategoriaRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

private val logger = KotlinLogging.logger {}

@Service
class CategoriaService @Autowired constructor(
    val categoriaRepository: CategoriaRepository,
) {

    fun createCategoria(categoria: Categoria): Categoria {
        logger.debug { "creating categoria..." }

        // should validate if categoria already exists
        validateIfCategoriaWithNameAlreadyExists(categoria.name)

        categoriaRepository.save(categoria)

        logger.debug { "categoria created with id ${categoria.id} and name ${categoria.name}" }

        return categoria
    }

    fun updateCategoria(categoria: Categoria): Categoria {
        logger.debug { "updating categoria ID: ${categoria.id} and name: ${categoria.name}" }
        categoriaRepository.save(categoria)
        logger.debug { "updated categoria ID: ${categoria.id} and name: ${categoria.name}" }
        return categoria
    }

    fun getCategorias(): List<Categoria> {
        logger.debug { "getting categorias..." }
        return categoriaRepository.findAll().toList()
    }

    fun getCategoriaById(idCategoria: String): Categoria {
        logger.debug { "getting categoria by id $idCategoria" }
        return categoriaRepository
            .findById(UUID.fromString(idCategoria))
            .orElseThrow { NotFoundException("Categoria $idCategoria not found") }
    }

    fun getCategoriaByName(name: String): Categoria {
        logger.debug { "getting categoria by name $name" }
        return categoriaRepository
            .findByName(name)
            .orElseThrow { NotFoundException("Categoria $name not found") }
    }

    fun deleteCategoria(idCategoria: String) {
        logger.debug { "deleting categoria by id $idCategoria" }
        categoriaRepository.deleteById(UUID.fromString(idCategoria))
    }

    private fun validateIfCategoriaWithNameAlreadyExists(name: String) {
        logger.debug { "validating if categoria $name already exists" }
        categoriaRepository
            .findByName(name)
            .orElseThrow { EntityAlreadyExistsException("Categoria com o nome $name já existente") }
    }
}
