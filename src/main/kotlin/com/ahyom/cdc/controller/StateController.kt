package com.ahyom.cdc.controller

import com.ahyom.cdc.domain.mapper.StateMapper
import com.ahyom.cdc.domain.request.StateRequest
import com.ahyom.cdc.service.StateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/states")
class StateController @Autowired constructor(
    private val stateService: StateService,
    private val stateMapper: StateMapper,
) {

    @PostMapping
    fun createState(
        @RequestBody
        stateRequest: StateRequest,
    ): ResponseEntity<StateRequest> {
        val state = stateMapper.toEntity(stateRequest)
        val stateCreated = stateService.createState(state)
        return ResponseEntity.status(HttpStatus.CREATED).body(stateMapper.fromEntity(stateCreated))
    }

    @PutMapping
    fun updateState(
        @RequestBody
        stateRequest: StateRequest,
    ): ResponseEntity<StateRequest> {
        val state = stateMapper.toEntity(stateRequest)
        val stateUpdated = stateService.updateState(state)
        return ResponseEntity.status(HttpStatus.OK).body(stateMapper.fromEntity(stateUpdated))
    }

    @GetMapping
    fun getAllStates() : ResponseEntity<List<StateRequest>> {
        val states = stateService.getStates()
        return ResponseEntity.ok(states.map { stateMapper.fromEntity(it) })
    }
}
