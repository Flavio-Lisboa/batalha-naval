package com.batalhanaval.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PacoteUpdateModel {

    public Long temaId;
    public String tipoPagamento;
    public BigDecimal valor;
    public String nomeTema;
    public String nomeAvatar;
    public String nomeEmbarcacoes;
    public String imageFundo;
    public String imageAvatar;
    public String barco4File;
    public String barco3File;
    public String barco2File;
    public String barco1File;
    public String barcosFile;
}
