package com.ahyom.cdc.domain.request

import jakarta.validation.Validation
import java.util.UUID

class StateRequest(
    var id: UUID?,
    val name: String,
    val country: CountryRequest?,
) {

    fun validate(): List<String> {
        val validator = Validation.buildDefaultValidatorFactory().validator
        return validator.validate(this).map { it.message }
    }
}
