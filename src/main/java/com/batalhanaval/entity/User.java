package com.batalhanaval.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    public String getDiamante() {
        return diamante;
    }

    public void setDiamante(String diamante) {
        this.diamante = diamante;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public float getVolumeMusica() {
        return volumeMusica;
    }

    public void setVolumeMusica(float volumeMusica) {
        this.volumeMusica = volumeMusica;
    }

    public float getVolumeSom() {
        return volumeSom;
    }

    public void setVolumeSom(float volumeSom) {
        this.volumeSom = volumeSom;
    }

    public String getSrcAvatar() {
        return srcAvatar;
    }

    public void setSrcAvatar(String srcAvatar) {
        this.srcAvatar = srcAvatar;
    }

    public int getTrofeus() {
        return trofeus;
    }

    public void setTrofeus(int trofeus) {
        this.trofeus = trofeus;
    }
    public int getVitorias() {
        return vitorias;
    }

    public void setVitorias(int vitorias) {
        this.vitorias = vitorias;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private LocalDateTime dataNascimento;
    private NivelAcesso nivelAcesso;
    private String diamante;
    private String moeda;
    private float volumeMusica;
    private float volumeSom;
    private String srcAvatar;
    private int trofeus;
    private int vitorias;
}
