package com.ahyom.cdc.domain.repository

import com.ahyom.cdc.domain.entity.Books
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface BooksRepository : CrudRepository<Books, UUID> {

    fun findByIsbn(isbn: String): Optional<Books>
}
