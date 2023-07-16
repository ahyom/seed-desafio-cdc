package com.ahyom.cdc.service

import com.ahyom.cdc.domain.entity.Autor
import com.ahyom.cdc.domain.repository.AutorRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class AutorService @Autowired constructor(
    val autorRepository: AutorRepository
) {

    fun createAutor(autor: Autor): Autor {
        logger.debug { "creating autor..." }
        autorRepository.save(autor)
        logger.debug { "autor created with id ${autor.id}" }
        return autor
    }
}
