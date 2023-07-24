package com.ahyom.cdc.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
@Transactional
class CategoriaControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
) {

    @Test
    fun `should return 201 HTTP when try to create some category`() {
        TODO()
    }

    @Test
    fun `should return 200 HTTP when try to get all categories`() {
        TODO()
    }

    @Test
    fun `should return 200 HTTP when try to get some category by id`() {
        TODO()
    }

    @Test
    fun `should return 200 HTTP when try to get some category by name`() {
        TODO()
    }

    @Test
    fun `should return 200 HTTP when try to get some category by name with case sensitive`() {
        TODO()
    }

    @Test
    fun `should return 404 HTTP when try to get some category by id that not exists`() {
        TODO()
    }

    @Test
    fun `should return 400 HTTP when try to create some category without name`() {
        TODO()
    }

    @Test
    fun `should return 400 HTTP when try to create some category with name already exists`() {
        TODO()
    }
}
