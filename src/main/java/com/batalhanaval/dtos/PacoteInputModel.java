package com.batalhanaval.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
public class PacoteInputModel {

    private MultipartFile imageFundo;
    private MultipartFile imageAvatar;
    private MultipartFile barco4File;
    private MultipartFile barco3File;
    private MultipartFile barco2File;
    private MultipartFile barco1File;
    private MultipartFile barcosFile;
    private String tipoPagamento;
    private BigDecimal valor;
    private String nomeTema;
    private String nomeAvatar;
    private String nomeEmbarcacoes;
}
