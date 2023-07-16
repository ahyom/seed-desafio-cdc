package com.ahyom.cdc.service

import com.ahyom.cdc.domain.entity.Autor
import com.ahyom.cdc.domain.repository.AutorRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

private val logger = KotlinLogging.logger {}

@Service
class AutorService @Autowired constructor(
    val autorRepository: AutorRepository,
) {
    fun createAutor(autor: Autor): Autor {
        logger.debug { "creating autor..." }
        autorRepository.save(autor)
        logger.debug { "autor created with id ${autor.id}" }
        return autor
    }

    // TODO -> update autor

    fun getAutors(): List<Autor> {
        logger.debug { "getting autors..." }
        return autorRepository.findAll().toList()
    }

    fun getAutorById(autorId: UUID): Autor {
        logger.debug { "getting autor by id $autorId" }
        // TODO -> should return a 404 if not found
        return autorRepository.findById(autorId).orElseThrow()
    }

    fun deleteAutor(autorId: UUID) {
        logger.debug { "deleting autor by id $autorId" }
        // TODO -> should return a 404 if not found
        autorRepository.deleteById(autorId)
    }
}
