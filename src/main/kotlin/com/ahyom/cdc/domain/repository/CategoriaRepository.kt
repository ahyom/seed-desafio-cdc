package com.ahyom.cdc.domain.repository

import com.ahyom.cdc.domain.entity.Categoria
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface CategoriaRepository : CrudRepository<Categoria, UUID> {

    fun findByName(name: String): Optional<Categoria>
}
