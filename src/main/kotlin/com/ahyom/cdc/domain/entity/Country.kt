package com.ahyom.cdc.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "tb_country")
class Country(

    @Id
    var id: UUID,

    @Column(name = "name", nullable = false, unique = true)
    var name: String,

    @OneToMany
    @JoinColumn(name = "id_state", referencedColumnName = "id")
    var states: List<State>?,
)
