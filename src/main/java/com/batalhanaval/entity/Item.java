package com.batalhanaval.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal valor;
    private String tipoPagamento;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] image;
    private Boolean ativo;

    @ManyToOne
    private ItemCategoria itemCategoria;
}
