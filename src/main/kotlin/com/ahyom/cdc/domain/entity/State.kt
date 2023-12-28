package com.ahyom.cdc.domain.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "tb_state")
class State(

    @Id
    var id: UUID,

    @Column(name = "name", nullable = false, unique = true)
    var name: String,
)
