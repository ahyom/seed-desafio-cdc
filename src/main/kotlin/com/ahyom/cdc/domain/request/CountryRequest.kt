package com.ahyom.cdc.domain.request

import jakarta.validation.Validation
import jakarta.validation.constraints.NotBlank
import java.util.UUID

class CountryRequest(
    var id: UUID?,

    @field:NotBlank(message = "Name is required")
    val name: String,

    val states: List<String>?,
) {

    fun validate(): List<String> {
        val validator = Validation.buildDefaultValidatorFactory().validator
        return validator.validate(this).map { it.message }
    }
}
