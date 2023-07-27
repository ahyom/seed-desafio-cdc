package com.ahyom.cdc.service

import com.ahyom.cdc.domain.entity.Livro
import com.ahyom.cdc.domain.exception.EntityAlreadyExistsException
import com.ahyom.cdc.domain.exception.NotFoundException
import com.ahyom.cdc.domain.repository.LivrosRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

private val logger = KotlinLogging.logger {}

@Service
class LivroService @Autowired constructor(
    val livrosRepository: LivrosRepository,
) {

    fun createLivro(livro: Livro): Livro {
        logger.debug { "creating livro..." }

        // should validate if livro with ISBN already exists
        validateIfISBNAlreadyExists(livro.isbn)

        livrosRepository.save(livro)

        logger.debug { "livro created with id ${livro.id} and isbn ${livro.isbn}" }

        return livro
    }

    fun updateLivro(livro: Livro): Livro {
        logger.debug { "updating livro ID: ${livro.id} and isbn: ${livro.isbn}" }

        livrosRepository.save(livro)

        logger.debug { "updated livro ID: ${livro.id} and isbn: ${livro.isbn}" }

        return livro
    }

    fun getLivros(): List<Livro> {
        logger.debug { "getting livros..." }
        return livrosRepository.findAll().toList()
    }

    fun getLivroById(idLivro: UUID): Livro {
        logger.debug { "getting livro by id $idLivro" }
        return livrosRepository
            .findById(idLivro)
            .orElseThrow { NotFoundException("Livro $idLivro not found") }
    }

    fun deleteLivro(idLivro: UUID) {
        logger.debug { "deleting livro by id $idLivro" }
        livrosRepository.deleteById(idLivro)
    }

    fun validateIfISBNAlreadyExists(isbn: String) {
        logger.debug { "validating if livro with isbn $isbn already exists" }

        val livros = livrosRepository.findByIsbn(isbn)

        if (livros.isPresent) {
            logger.error { "livro with isbn $isbn already exists" }
            throw EntityAlreadyExistsException("Livro with isbn $isbn already exists")
        }
    }
}
