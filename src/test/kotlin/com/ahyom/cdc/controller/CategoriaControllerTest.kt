package com.ahyom.cdc.controller

import com.ahyom.cdc.domain.entity.Categoria
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime
import java.util.UUID

private const val BASE_ENDPOINT = "/categoria"

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
class CategoriaControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
) {

    private lateinit var categoria: Categoria

    private var idCategoria = "db540faf-a5e4-4122-b54c-653fc3ed5c4f"
    private var nameCategoria = "Java"

    @BeforeEach
    fun setup() {
        categoria = Categoria(
            id = UUID.fromString(idCategoria),
            name = nameCategoria,
            createdAt = LocalDateTime.now(),
        )
    }

    @Test
    fun `a_should return 201 HTTP when try to create some category`() {
        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoria)),
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.name").value("Java"))
            .andExpect(jsonPath("$.created_at").exists())
    }

    @Test
    fun `should return 200 HTTP when try to get all categories`() {
        mockMvc.perform(
            get(BASE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").exists())
    }

    @Test
    fun `b_should return 200 HTTP when try to get some category by id`() {
        mockMvc.perform(
            get("$BASE_ENDPOINT/$idCategoria")
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(idCategoria))
            .andExpect(jsonPath("$.name").value(categoria.name))
    }

    @Test
    fun `should return 404 HTTP when try to get some category by id that not exists`() {
        mockMvc.perform(
            get("$BASE_ENDPOINT/${UUID.randomUUID()}")
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should return 400 HTTP when try to create some category without name`() {
        categoria.name = ""

        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoria)),
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `should return 400 HTTP when try to create some category with name already exists`() {
        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoria)),
        )
            .andExpect(status().isBadRequest)
    }
}
