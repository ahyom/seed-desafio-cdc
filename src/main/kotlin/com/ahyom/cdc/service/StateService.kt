package com.ahyom.cdc.service

import com.ahyom.cdc.domain.entity.State
import com.ahyom.cdc.domain.exception.NotFoundException
import com.ahyom.cdc.domain.repository.StateRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

private val logger = KotlinLogging.logger {}

@Service
class StateService @Autowired constructor(
    private val stateRepository: StateRepository,
) {

    fun createState(state: State): State {
        logger.debug { "creating state..." }
        return stateRepository.save(state)
    }

    fun updateState(state: State): State {
        logger.debug { "updating state ID: ${state.id} and name: ${state.name}" }
        return stateRepository.save(state)
    }

    fun getStates(): List<State> {
        logger.debug { "getting states..." }
        return stateRepository.findAll().toList()
    }

    fun getStateById(idState: String): State {
        logger.debug { "getting state by id $idState" }
        return stateRepository
            .findById(UUID.fromString(idState))
            .orElseThrow { NotFoundException("State $idState not found") }
    }

    fun deleteState(idState: String) {
        logger.debug { "deleting state by id $idState" }
        return stateRepository.deleteById(UUID.fromString(idState))
    }
}
