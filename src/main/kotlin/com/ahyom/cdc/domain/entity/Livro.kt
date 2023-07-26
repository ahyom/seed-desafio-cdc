package com.ahyom.cdc.domain.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import java.time.LocalDateTime
import java.util.UUID

@Entity(name = "tb_livros")
class Livro(

    @Id
    var id: UUID,

    @Column(nullable = false)
    var titulo: String,

    @Column(nullable = false, length = 500)
    var resumo: String,

    @Column(nullable = false)
    var sumario: String,

    @Column(nullable = false)
    var preco: Double,

    @Column(nullable = false)
    var numeroPaginas: Int,

    @Column(nullable = false)
    var isbn: String,

    var dataPublicacao: LocalDateTime,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    var categoria: Categoria,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "autor_id", referencedColumnName = "id")
    var autor: Autor,

    @Column(nullable = false)
    var createdAt: LocalDateTime,
)
