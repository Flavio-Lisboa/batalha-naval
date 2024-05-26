package com.batalhanaval.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Data
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private Long id;
    private String nome;
    private Double valor;
    private String tipoPagamento;
    private String tipoItem;
    public byte[] imagem;
}
