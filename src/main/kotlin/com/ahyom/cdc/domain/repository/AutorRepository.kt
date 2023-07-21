package com.ahyom.cdc.domain.repository

import com.ahyom.cdc.domain.entity.Autor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AutorRepository : CrudRepository<Autor, UUID> {

    fun findByEmail(email: String): List<Autor>
}
