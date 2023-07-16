package com.ahyom.cdc.controller

import com.ahyom.cdc.model.mapper.AutorMapper
import com.ahyom.cdc.model.request.AutorRequest
import com.ahyom.cdc.service.AutorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/autor")
class AutorController @Autowired constructor(
    val autorService: AutorService,
    val autorMapper: AutorMapper,
) {

    @PostMapping
    fun createAutor(@RequestBody autorRequest: AutorRequest): ResponseEntity<AutorRequest> {
        val autor = autorMapper.toEntity(autorRequest)

        val autorCreated = autorService.createAutor(autor)

        return ResponseEntity.ok(autorMapper.fromEntity(autorCreated))
    }
}
