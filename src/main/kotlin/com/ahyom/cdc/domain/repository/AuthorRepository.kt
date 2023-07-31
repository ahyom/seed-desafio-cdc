package com.ahyom.cdc.domain.repository

import com.ahyom.cdc.domain.entity.Author
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface AuthorRepository : CrudRepository<Author, UUID> {

    fun findByEmail(email: String): Optional<List<Author>>
}
