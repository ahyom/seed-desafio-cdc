package com.ahyom.cdc.domain.request

import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime
import java.util.UUID

class CategoriaRequest(

    var id: UUID?,

    @field:NotBlank(message = "Nome da categoria é obrigatorio")
    var name: String,

    var createdAt: LocalDateTime?,
)
