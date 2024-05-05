package com.batalhanaval.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ItemModel {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal valor;
    private String tipoPagamento;
    private String imageData;
    private Boolean ativo;
    private Long categoriaId;
    private MultipartFile image;
}
