package com.ahyom.cdc.service

import com.ahyom.cdc.domain.entity.Autor
import com.ahyom.cdc.domain.exception.EntityAlreadyExistsException
import com.ahyom.cdc.domain.exception.NotFoundException
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

        // should validate if email already exists
        validateIfEmailAlreadyExists(autor.email)

        // will save autor and log information
        autorRepository.save(autor)

        logger.debug { "autor created with id ${autor.id}" }
        return autor
    }

    fun updateAutor(autor: Autor, autorId: UUID): Autor {
        logger.debug { "updating autor ID: $autorId and name: ${autor.name}" }
        autorRepository.save(autor)
        logger.debug { "updated autor ID: $autorId and name: ${autor.name}" }
        return autor
    }

    fun getAutors(): List<Autor> {
        logger.debug { "getting autors..." }
        return autorRepository.findAll().toList()
    }

    fun getAutorById(autorId: UUID): Autor {
        logger.debug { "getting autor by id $autorId" }
        return autorRepository
            .findById(autorId)
            .orElseThrow { NotFoundException("Autor $autorId not found") }
    }

    fun deleteAutor(autorId: UUID) {
        logger.debug { "deleting autor by id $autorId" }
        autorRepository.deleteById(autorId)
    }

    private fun validateIfEmailAlreadyExists(email: String) {
        logger.debug { "validating if email $email already exists" }
        autorRepository
            .findByEmail(email)
            .orElseThrow { EntityAlreadyExistsException("Autor com o email $email já existente") }
    }
}
