package com.batalhanaval.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemModel {

    private String nome;
    private String descricao;
    private BigDecimal valor;
    private String tipoPagamento;
    //private String imageUrl;
    private Boolean ativo;
    private Long categoriaId;
}
