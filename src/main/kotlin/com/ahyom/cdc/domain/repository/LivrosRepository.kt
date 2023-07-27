package com.ahyom.cdc.domain.repository

import com.ahyom.cdc.domain.entity.Livro
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface LivrosRepository : CrudRepository<Livro, UUID> {

    fun findByIsbn(isbn: String): Optional<Livro>
}
