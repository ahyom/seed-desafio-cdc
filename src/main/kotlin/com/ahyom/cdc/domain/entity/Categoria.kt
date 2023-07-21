package com.ahyom.cdc.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "tb_categoria")
class Categoria(

    @Id
    var id: UUID,

    @Column(nullable = false, unique = true)
    var name: String,

    @Column(nullable = false)
    var createdAt: LocalDateTime,
)
