package com.batalhanaval.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String tipoPagamento;
    private BigDecimal valor;
    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] imgFundo;
}
