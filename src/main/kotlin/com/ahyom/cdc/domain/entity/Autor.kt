package com.ahyom.cdc.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "tb_autor")
class Autor(

    @Id
    var id: UUID,

    @Column(nullable = false, unique = false)
    var name: String,

    @Column(nullable = false, unique = false)
    var email: String,

    @Column(length = 400, nullable = false, unique = false)
    var description: String,

    @Column(nullable = false, unique = false)
    var createdAt: LocalDateTime,
)
