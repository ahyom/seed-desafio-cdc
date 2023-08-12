package com.ahyom.cdc.controller

import com.ahyom.cdc.domain.mapper.CountryMapper
import com.ahyom.cdc.domain.request.CountryRequest
import com.ahyom.cdc.service.CountryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("countries")
class CountryController @Autowired constructor(
    val countryService: CountryService,
    val countryMapper: CountryMapper,
) {

    @PostMapping
    fun createCountry(
        @RequestBody
        countryRequest: CountryRequest,
    ): ResponseEntity<CountryRequest> {
        val country = countryMapper.toEntity(countryRequest)
        val countryCreated = countryService.saveCountry(country)
        return ResponseEntity.status(HttpStatus.CREATED).body(countryMapper.fromEntity(countryCreated))
    }

    @GetMapping
    fun getCountries(): ResponseEntity<List<CountryRequest>> {
        val countries = countryService.getCountries()
        return ResponseEntity.ok(countries.map { countryMapper.fromEntity(it) })
    }

    @GetMapping("/{country-id}")
    fun getCountryById(@PathVariable("country-id") countryId: String): ResponseEntity<CountryRequest> {
        val country = countryService.getCountryById(UUID.fromString(countryId))
        return ResponseEntity.ok(countryMapper.fromEntity(country))
    }
}
