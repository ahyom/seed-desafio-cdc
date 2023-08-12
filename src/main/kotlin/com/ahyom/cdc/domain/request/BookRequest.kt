package com.ahyom.cdc.domain.request

import jakarta.validation.Validation
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Past
import jakarta.validation.constraints.Size
import java.time.LocalDateTime
import java.util.UUID

data class BookRequest(
    var id: UUID?,

    @field:NotBlank(message = "Titulo é obrigatório")
    val title: String,

    @field:NotBlank(message = "Resumo é obrigatório")
    @field:Size(max = 500, message = "Resumo deve ter no máximo 500 caracteres")
    val summary: String,

    val tableOfContents: String,

    @field:Min(20, message = "Preço deve ser maior que R$20,00")
    val price: Double,

    @field:Min(100, message = "Número de páginas deve ser maior que 100")
    val pageNumbers: Int,

    @field:NotBlank(message = "ISBN é obrigatório")
    val isbn: String,

    @field:Past(message = "Data de publicação deve ser a partir da data atual")
    val publishDate: LocalDateTime,

    val category: CategoryRequest,

    val author: AuthorRequest,

    var createdAt: LocalDateTime?,
) {

    fun validate(): List<String> {
        val validator = Validation.buildDefaultValidatorFactory().validator
        return validator.validate(this).map { it.message }
    }
}
