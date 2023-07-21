package com.ahyom.cdc.domain.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime
import java.util.UUID

data class AutorRequest(

    var id: UUID?,

    @field:NotBlank(message = "Name is required")
    var name: String,

    @NotBlank
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Email is invalid")
    var email: String,

    @field:NotBlank(message = "Description is required")
    @field:Size(max = 400, message = "Description must be less than 400 characters")
    var description: String,

    var createdAt: LocalDateTime?,

)
