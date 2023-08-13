package com.ahyom.cdc.service

import com.ahyom.cdc.domain.entity.Country
import com.ahyom.cdc.domain.exception.NotFoundException
import com.ahyom.cdc.domain.repository.CountryRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

private val logger = KotlinLogging.logger {}

@Service
class CountryService @Autowired constructor(
    val countryRepository: CountryRepository,
) {

    fun saveCountry(country: Country): Country {
        logger.debug { "creating country..." }
        return countryRepository.save(country)
    }

    fun updateCountry(country: Country): Country {
        logger.debug { "updating country ID: ${country.id} and name: ${country.name}" }
        return countryRepository.save(country)
    }

    fun getCountries(): List<Country> {
        logger.debug { "getting countries..." }
        val countries = countryRepository.findAll().toList()
        return countries
    }

    fun getCountryById(idCountry: UUID): Country {
        logger.debug { "getting country by id $idCountry" }
        return countryRepository
            .findById(idCountry)
            .orElseThrow { NotFoundException("Country $idCountry not found") }
    }
}
