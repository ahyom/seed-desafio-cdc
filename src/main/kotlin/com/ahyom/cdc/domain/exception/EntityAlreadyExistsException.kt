package com.ahyom.cdc.domain.exception

class EntityAlreadyExistsException(message: String) : RuntimeException(message) {
    companion object { private const val serialVersionUID: Long = 8481847006406469617L }
}