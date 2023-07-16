package com.ahyom.cdc.model.entity

import java.time.LocalDateTime
import java.util.UUID

class Autor(
    var id: UUID,
    var name: String,
    var email: String,
    var description: String,
    var createdAt: LocalDateTime,
)