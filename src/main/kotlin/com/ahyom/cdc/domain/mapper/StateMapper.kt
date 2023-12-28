package com.ahyom.cdc.domain.mapper

import com.ahyom.cdc.domain.entity.State
import com.ahyom.cdc.domain.request.StateRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class StateMapper : Mapper<StateRequest, State> {

    override fun fromEntity(entity: State): StateRequest {
        return StateRequest(
            id = entity.id,
            name = entity.name,
        )
    }

    override fun toEntity(domain: StateRequest): State {
        domain.validate().let {
            if (it.isNotEmpty()) {
                throw IllegalArgumentException(it.toString())
            }
        }

        if (domain.id == null) {
            domain.id = UUID.randomUUID()
        }

        return State(
            id = domain.id!!,
            name = domain.name,
        )
    }
}
