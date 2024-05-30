package com.batalhanaval.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JogadorConfig {

    @Value("${jogador.default.jogador1}")
    private String jogador1;

    @Value("${jogador.default.jogador2}")
    private String jogador2;

    public String getJogador1() {
        return jogador1;
    }

    public String getJogador2() {
        return jogador2;
    }
}