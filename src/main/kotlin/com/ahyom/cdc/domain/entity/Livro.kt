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
    var title: String,

    @Column(nullable = false, length = 500)
    var summary: String,

    @Column(nullable = false)
    var tableOfContents: String,

    @Column(nullable = false)
    var price: Double,

    @Column(nullable = false)
    var pageNumbers: Int,

    @Column(nullable = false)
    var isbn: String,

    var publishDate: LocalDateTime,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    var categoria: Category,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "autor_id", referencedColumnName = "id")
    var autor: Author,

    @Column(nullable = false)
    var createdAt: LocalDateTime,
)
