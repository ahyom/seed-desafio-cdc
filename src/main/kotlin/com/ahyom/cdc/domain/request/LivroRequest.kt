package com.ahyom.cdc.domain.request

import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime
import java.util.UUID

data class LivroRequest(
    var id: UUID?,

    @field:NotBlank(message = "Título do livro é obrigatorio")
    val title: String,

    @field:NotBlank(message = "Resumo do livro é obrigatorio")
    val summary: String,

    @field:NotBlank(message = "Sumário do livro é obrigatorio")
    val tableOfContents: String,

    @field:NotBlank(message = "Preço do livro é obrigatorio")
    val price: Double,

    @field:NotBlank(message = "Número de páginas do livro é obrigatorio")
    val pageNumbers: Int,

    @field:NotBlank(message = "ISBN do livro é obrigatorio")
    val isbn: String,

    @field:NotBlank(message = "Data de publicação do livro é obrigatoria")
    val publishDate: LocalDateTime,

    @field:NotBlank(message = "Categoria do livro é obrigatoria")
    val categoria: CategoriaRequest,

    @field:NotBlank(message = "Autor do livro é obrigatorio")
    val autor: AutorRequest,

    var createdAt: LocalDateTime?,
)
