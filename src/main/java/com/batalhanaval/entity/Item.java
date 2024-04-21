package com.batalhanaval.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Item {

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BigDecimal getPrecoMoeda() {
        return precoMoeda;
    }

    public void setPrecoMoeda(BigDecimal precoMoeda) {
        this.precoMoeda = precoMoeda;
    }

    public BigDecimal getPrecoDiamante() {
        return precoDiamante;
    }

    public void setPrecoDiamante(BigDecimal precoDiamante) {
        this.precoDiamante = precoDiamante;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ItemCategoria getItemCategoria() {
        return itemCategoria;
    }

    public void setItemCategoria(ItemCategoria itemCategoria) {
        this.itemCategoria = itemCategoria;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String desc;
    private BigDecimal precoMoeda;
    private BigDecimal precoDiamante;
    private String imageUrl;
    @ManyToOne
    private ItemCategoria itemCategoria;

}
