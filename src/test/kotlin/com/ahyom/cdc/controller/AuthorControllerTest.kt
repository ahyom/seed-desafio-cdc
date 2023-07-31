package com.ahyom.cdc.controller

import com.ahyom.cdc.domain.entity.Author
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

private const val BASE_ENDPOINT = "/autor"

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
@Transactional
class AuthorControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
) {

    private lateinit var author: Author

    private var idAutor = "db540faf-a5e4-4122-b54c-653fc3ed5c4f"

    @BeforeEach
    fun setup() {
        author = Author(
            id = UUID.fromString(idAutor),
            name = "John Frusciante",
            email = "frusciante@aol.com",
            description = "Musico e integrante dos Red Hot Chili Peppers",
            createdAt = LocalDateTime.now(),
        )
    }

    @Test
    fun `a should return 201 HTTP when try to create some Autor`() {
        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(author)),
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.created_at").exists())
    }

    @Test
    fun `should return 400 HTTP when try to create some Autor without name`() {
        author.name = ""

        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(author)),
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `should return 400 HTTP when try to create some Autor without email`() {
        author.email = ""

        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(author)),
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `should return 400 HTTP when try to create some Autor without description`() {
        author.description =  ""

        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(author)),
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `should return 400 HTTP when try to create some Autor with description greater than 400 bytes`() {

        author.description = "John Frusciante saiu do rhcp pq eh tonto John Frusciante saiu do rhcp pq eh tonto John Frusciante saiu do rhcp pq eh tonto John Frusciante saiu do rhcp pq eh tonto John Frusciante saiu do rhcp pq eh tonto John Frusciante saiu do rhcp pq eh tonto John Frusciante saiu do rhcp pq eh tonto John Frusciante saiu do rhcp pq eh tonto John Frusciante saiu do rhcp pq eh tonto John Frusciante saiu do rhcp pq eh tonto "

        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(author)),
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `should return 400 HTTP when try to create some Autor with invalid email`() {
        author.email = "invalid_email"

        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(author)),
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `should return 400 HTTP when try to create some Autor with an email that is already in use`() {
        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(author)),
        )
            .andExpect(status().isBadRequest)
    }
}
