package com.ahyom.cdc.model.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.UUID

private const val MAX_DESCRIPTION_SIZE = 400L

class AutorRequest(

    var id: UUID,

    @NotBlank
    @NotNull
    var name: String,

    @NotBlank
    @NotNull
    @Email
    var email: String,

    @NotBlank
    @NotNull
    @Max(MAX_DESCRIPTION_SIZE)
    var description: String,

)
