package com.batalhanaval.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDateTime getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDateTime dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public NivelAcesso getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(NivelAcesso nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    public int getDiamante() {
        return diamante;
    }

    public void setDiamante(int diamante) {
        this.diamante = diamante;
    }

    public int getMoeda() {
        return moeda;
    }

    public void setMoeda(int moeda) {
        this.moeda = moeda;
    }

    public int getVolumeMusica() {
        return volumeMusica;
    }

    public void setVolumeMusica(int volumeMusica) {
        this.volumeMusica = volumeMusica;
    }

    public int getVolumeSom() {
        return volumeSom;
    }

    public void setVolumeSom(int volumeSom) {
        this.volumeSom = volumeSom;
    }

    public int getVitorias() {
        return vitorias;
    }

    public void setVitorias(int vitorias) {
        this.vitorias = vitorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }

    public int getIdAvatar() {
        return idAvatar;
    }

    public void setIdAvatar(int idAvatar) {
        this.idAvatar = idAvatar;
    }

    public int getIdTema() {
        return idTema;
    }

    public void setIdTema(int idTema) {
        this.idTema = idTema;
    }

    public int getIdEmbarcacao() {
        return idEmbarcacao;
    }

    public void setIdEmbarcacao(int idEmbarcacao) {
        this.idEmbarcacao = idEmbarcacao;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private LocalDateTime dataNascimento;
    private NivelAcesso nivelAcesso;
    private int diamante;
    private int moeda;
    private int volumeMusica;
    private int volumeSom;
    private int vitorias;
    private int derrotas;
    private int idAvatar;
    private int idTema;
    private int idEmbarcacao;
}
