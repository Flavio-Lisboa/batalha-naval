package com.batalhanaval.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
public class PacoteModel {

    public Long temaId;
    public String nomeTema;
    public String tipoPagamento;
    public BigDecimal valor;
    public String nomeAvatar;
    public String nomeEmbarcacoes;
    public String fundoBase64;
    public String avatarBase64;
    public String barcosBase64;
    public String barco1Base64;
    public String barco2Base64;
    public String barco3Base64;
    public String barco4Base64;
}
