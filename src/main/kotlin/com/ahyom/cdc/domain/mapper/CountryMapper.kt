package com.ahyom.cdc.domain.mapper

import com.ahyom.cdc.domain.entity.Country
import com.ahyom.cdc.domain.request.CountryRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CountryMapper @Autowired constructor(
) : Mapper<CountryRequest, Country> {

    override fun fromEntity(entity: Country): CountryRequest {
        val states: List<String> = entity.states?.map { it.name } ?: emptyList()

        return CountryRequest(
            id = entity.id,
            name = entity.name,
            states = listOf(states.toString()),
        )
    }

    override fun toEntity(domain: CountryRequest): Country {
        domain.validate().let {
            if (it.isNotEmpty()) {
                throw IllegalArgumentException(it.toString())
            }
        }

        if (domain.id == null) {
            domain.id = UUID.randomUUID()
        }

        return Country(
            id = domain.id!!,
            name = domain.name,
            null,
        )
    }
}
