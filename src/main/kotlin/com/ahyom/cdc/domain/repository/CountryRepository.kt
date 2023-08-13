package com.ahyom.cdc.domain.repository

import com.ahyom.cdc.domain.entity.Country
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CountryRepository : CrudRepository<Country, UUID> {
    fun findByName(name: String): Country?
}
