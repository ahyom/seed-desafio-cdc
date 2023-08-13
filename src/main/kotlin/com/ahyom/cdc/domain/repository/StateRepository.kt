package com.ahyom.cdc.domain.repository

import com.ahyom.cdc.domain.entity.State
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface StateRepository : CrudRepository<State, UUID> {

    fun findByName(name: String): State?
}
