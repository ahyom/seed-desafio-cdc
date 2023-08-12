package com.ahyom.cdc.controller

import com.ahyom.cdc.domain.entity.Author
import com.ahyom.cdc.domain.entity.Books
import com.ahyom.cdc.domain.entity.Category
import com.ahyom.cdc.domain.exception.EntityAlreadyExistsException
import com.ahyom.cdc.domain.mapper.BookMapper
import com.ahyom.cdc.service.BooksService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime
import java.util.UUID

private const val BASE_ENDPOINT = "/books"

@SpringBootTest
@AutoConfigureMockMvc
class BooksControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
    @MockBean val booksService: BooksService,
    @MockBean val bookMapper: BookMapper,
) {

    private var idAuthor = UUID.fromString("0678b0a4-2aed-4122-b02c-c704fa6a24a9")
    private var idCategory = UUID.fromString("73870e23-5360-485e-a55c-e93920628399")
    private var idBook = UUID.fromString("ffbfc1dc-bab2-4cac-ae99-c8942073ca27")

    private lateinit var book: Books

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        book = buildBook()
    }

    @Test
    fun `should return 201 HTTP when try to create some Book`() {
        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(book)),
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").exists())
    }

    @Test
    fun `should return 200 HTTP when try to update some Book`() {
        book.pageNumbers = 200
        `when`(booksService.createBook(book)).thenReturn(book)

        mockMvc.perform(
            put("$BASE_ENDPOINT/$idBook")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(book)),
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.page_numbers").value(book.pageNumbers))
    }

    @Test
    fun `should return 200 HTTP when try to get a list of Books`() {
        `when`(booksService.getBooks()).thenReturn(listOf(book))

        mockMvc.perform(
            MockMvcRequestBuilders.get(BASE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").exists())
    }

    @Test
    fun `should return 200 HTTP when try to get some Book by ID`() {
        `when`(booksService.getBookById(idBook)).thenReturn(book)

        mockMvc.perform(
            MockMvcRequestBuilders.get("$BASE_ENDPOINT/$idBook")
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").exists())
    }

    @Test
    fun `should return 204 HTTP when try to delete some Book`() {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("$BASE_ENDPOINT/$idBook")
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpect(status().isNoContent)
    }

    @Test
    fun `should return 400 HTTP when try to create some Book with not title`() {
        book.title = ""
        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(book)),
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.cause").value("[Titulo é obrigatório]"))
    }

    @Test
    fun `should return 400 HTTP when try to create some Book with a title that already exists`() {
        `when`(booksService.createBook(book))
            .thenThrow(EntityAlreadyExistsException("Book with title ${book.title} already exists"))

        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(book)),
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.cause").value("Book with title ${book.title} already exists"))
    }

    @Test
    fun `should return 400 HTTP when try to create some Book with not summary`() {
        book.summary = ""
        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(book)),
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.cause").value("[Resumo é obrigatório]"))
    }

    @Test
    fun `should return 400 HTTP when try to create some Book and summary has more than 500 characters`() {
        book.summary = "Em um mundo futurista, a humanidade enfrenta desafios inimagináveis. A tecnologia " +
            "avançou além das expectativas, mas com isso veio uma ameaça nunca vista antes. A busca " +
            "pela sobrevivência força heróis improváveis a se unirem em uma jornada épica. Em cada " +
            "esquina, um segredo é revelado e alianças são testadas. Enfrentando criaturas inumanas " +
            "e dilemas éticos, eles devem encontrar força interna para superar os obstáculos. Uma " +
            "trama emocionante de ação, suspense e romance se desdobra enquanto o destino do mundo " +
            "está em jogo. O que eles sacrificariam para um amanhã melhor?"
        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(book)),
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.cause").value("[Resumo deve ter no máximo 500 caracteres]"))
    }

    @Test
    fun `should return 400 HTTP when try to create some Book with a price less then 20`() {
        book.price = 19.99
        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(book)),
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.cause").value("[Preço deve ser maior que R$20,00]"))
    }

    @Test
    fun `should return 400 HTTP when try to create some Book with less than 100 pages`() {
        book.pageNumbers = 99
        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(book)),
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.cause").value("[Número de páginas deve ser maior que 100]"))
    }

    @Test
    fun `should return 400 HTTP when try to create some Book with not ISBN`() {
        book.isbn = ""
        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(book)),
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.cause").value("[ISBN é obrigatório]"))
    }

    @Test
    fun `should return 400 HTTP when try to create some Book with a ISBN that already exists`() {
        `when`(booksService.createBook(book))
            .thenThrow(EntityAlreadyExistsException("Book with isbn ${book.isbn} already exists"))

        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(book)),
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.cause").value("Book with isbn ${book.isbn} already exists"))
    }

    @Test
    fun `should return 400 HTTP when try to create some Book with a publish date on the past`() {
        book.publishDate = LocalDateTime.now().minusDays(1)
        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(book)),
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.cause").value("[Data de publicação deve ser a partir da data atual]"))
    }

    private fun buildAuthor(): Author {
        return Author(
            id = idAuthor,
            name = "John Anthony Frusciante",
            email = "john@chilipeppers.com",
            description = "Musician and compositor",
            createdAt = LocalDateTime.now(),
        )
    }

    private fun buildCategory(): Category {
        return Category(
            id = idCategory,
            name = "Music",
            createdAt = LocalDateTime.now(),
        )
    }

    private fun buildBook(): Books {
        return Books(
            id = idBook,
            title = "The Empyrean",
            summary = "Released in 2009",
            tableOfContents = "10 songs using guitar and piano",
            price = 29.90,
            pageNumbers = 120,
            isbn = "978-85-8055-000-1",
            publishDate = LocalDateTime.now(),
            category = buildCategory(),
            author = buildAuthor(),
            createdAt = LocalDateTime.now(),
        )
    }
}
