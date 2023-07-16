package com.ahyom.cdc.service

import com.ahyom.cdc.model.entity.Autor
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class AutorService {

    fun createAutor(autor: Autor): Autor {
        logger.debug { "creating autor..." }

        // call repository to save autor

        logger.debug { "autor created with id ${autor.id}" }

        return autor
    }
}
