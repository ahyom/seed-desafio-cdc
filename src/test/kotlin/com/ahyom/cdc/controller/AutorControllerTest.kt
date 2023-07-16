package com.ahyom.cdc.controller

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

private const val BASE_ENDPOINT = "/autor"

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
class AutorControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
) {

    @Test
    fun `should create some autor with success and return 201 HTTP Code`() {
        val autorRequest = """
            {
                "name": "John Frusciante",
                "email": "john@frusciante.com",
                "description": "Musico e integrante dos Red Hot Chili Peppers"
            }
        """.trimIndent()

        mockMvc.perform(
            post(BASE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(autorRequest),
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.created_at").exists())
    }

    @Test
    fun `should return 400 HTTP when try to create some Autor without name`() {}

    @Test
    fun `should return 400 HTTP when try to create some Autor without email`() {}

    @Test
    fun `should return 400 HTTP when try to create some Autor without description`() {}

    @Test
    fun `should return 400 HTTP when try to create some Autor with description greater than 400 bytes`() {}

    @Test
    fun `should return 400 HTTP when try to create some Autor with invalid email`() {}
}
