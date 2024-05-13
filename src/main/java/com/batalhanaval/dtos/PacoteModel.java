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
    public String fundoBase64;
    public String avatarBase64;
    public String barcosBase64;
}
