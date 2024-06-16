package com.batalhanaval.dtos;

import com.batalhanaval.entity.NivelAcesso;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private LocalDateTime dataNascimento;
    private NivelAcesso nivelAcesso;
    private int diamante;
    private int moeda;
    private float volumeMusica;
    private float volumeSom;
    private int vitorias;
    private int derrotas;
    private int idAvatar;
    private int idTema;
    private int idEmbarcacao;
    private String avatarBase64;
}
