package com.ahyom.cdc.controller

import com.ahyom.cdc.domain.entity.Autor
import com.ahyom.cdc.domain.entity.Categoria
import com.ahyom.cdc.domain.entity.Livro
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

private const val BASE_ENDPOINT = "/livros"

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
@Transactional
class LivrosControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
) {

    private var idAutor = UUID.fromString("0678b0a4-2aed-4122-b02c-c704fa6a24a9")
    private var idCategoria = UUID.fromString("73870e23-5360-485e-a55c-e93920628399")
    private var idLivro = UUID.fromString("ffbfc1dc-bab2-4cac-ae99-c8942073ca27")

    private lateinit var livro: Livro

    @BeforeEach
    fun setup() {
        livro = buildLivro()
    }

    // TODO -> improve those test scenarios

    @Test
    fun `should return 201 HTTP when try to create some Livro`() {
        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(livro)),
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").exists())
    }

    @Test
    fun `should return 200 HTTP when try to update some Livro`() {
        livro.pageNumbers = 200
        mockMvc.perform(
            put("$BASE_ENDPOINT/$idLivro")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(livro)),
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.page_numbers").value(livro.pageNumbers))
    }

    @Test
    fun `should return 200 HTTP when try to get a list of Livros`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get(BASE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").exists())
    }

    @Test
    fun `should return 200 HTTP when try to get some Livro by ID`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("$BASE_ENDPOINT/$idLivro")
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").exists())
    }

    @Test
    fun `should return 200 HTTP when try to delete some Livro`() {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("$BASE_ENDPOINT/$idLivro")
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpect(status().isNoContent)
    }

    @Test
    fun `should return 400 HTTP when try to create some Livro with not title`() {
        livro.title = ""
        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(livro)),
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.cause").value("[Titulo é obrigatório]"))
    }

//    @Test
//    fun `should return 400 HTTP when try to create some Livro with a title that already exists`() {
//        TODO()
//    }

    @Test
    fun `should return 400 HTTP when try to create some Livro with not summary`() {
        livro.summary = ""
        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(livro)),
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.cause").value("[Resumo é obrigatório]"))
    }

    @Test
    fun `should return 400 HTTP when try to create some Livro and summary has more than 500 characters`() {
        livro.summary = "Em um mundo futurista, a humanidade enfrenta desafios inimagináveis. A tecnologia avançou além das expectativas, mas com isso veio uma ameaça nunca vista antes. A busca pela sobrevivência força heróis improváveis a se unirem em uma jornada épica. Em cada esquina, um segredo é revelado e alianças são testadas. Enfrentando criaturas inumanas e dilemas éticos, eles devem encontrar força interna para superar os obstáculos. Uma trama emocionante de ação, suspense e romance se desdobra enquanto o destino do mundo está em jogo. O que eles sacrificariam para um amanhã melhor?"
        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(livro)),
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.cause").value("[Resumo deve ter no máximo 500 caracteres]"))
    }

    @Test
    fun `should return 400 HTTP when try to create some Livro with a price less then 20`() {
        livro.price = 19.99
        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(livro)),
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.cause").value("[Preço deve ser maior que R$20,00]"))
    }

    @Test
    fun `should return 400 HTTP when try to create some Livro with less than 100 pages`() {
        livro.pageNumbers = 99
        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(livro)),
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.cause").value("[Número de páginas deve ser maior que 100]"))
    }

    @Test
    fun `should return 400 HTTP when try to create some Livro with not ISBN`() {
        livro.isbn = ""
        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(livro)),
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.cause").value("[ISBN é obrigatório]"))
    }

    @Test
    fun `should return 400 HTTP when try to create some Livro with a ISBN that already exists`() {
        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(livro)),
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.cause").value("Livro with isbn ${livro.isbn} already exists"))
    }

    @Test
    fun `should return 400 HTTP when try to create some Livro with a publish date on the past`() {
        livro.publishDate = LocalDateTime.now().minusDays(1)
        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(livro)),
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.cause").value("[Data de publicação deve ser a partir da data atual]"))
    }

    private fun buildAutor(): Autor {
        return Autor(
            id = idAutor,
            name = "John Anthony Frusciante",
            email = "john@chilipeppers.com",
            description = "Musician and compositor",
            createdAt = LocalDateTime.now(),
        )
    }

    private fun buildCategoria(): Categoria {
        return Categoria(
            id = idCategoria,
            name = "Music",
            createdAt = LocalDateTime.now(),
        )
    }

    private fun buildLivro(): Livro {
        return Livro(
            id = idLivro,
            title = "The Empyrean",
            summary = "Released in 2009",
            tableOfContents = "10 songs using guitar and piano",
            price = 29.90,
            pageNumbers = 120,
            isbn = "978-85-8055-000-1",
            publishDate = LocalDateTime.now(),
            categoria = buildCategoria(),
            autor = buildAutor(),
            createdAt = LocalDateTime.now(),
        )
    }
}
