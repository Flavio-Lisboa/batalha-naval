package com.batalhanaval.controller;

import com.batalhanaval.config.JogadorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EscolherJogadorController {

    @Autowired
    private JogadorConfig jogadorConfig;

    @GetMapping("/escolher-jogador-inicio")
    public String escolherJogadorInicio(
            @RequestParam(required = false) String jogador1,
            @RequestParam(required = false) String jogador2) {

        // Use valores padrão se os parâmetros não forem fornecidos
        String jogador1Final = (jogador1 != null) ? jogador1 : jogadorConfig.getJogador1();
        String jogador2Final = (jogador2 != null) ? jogador2 : jogadorConfig.getJogador2();

        // Gera um número aleatório entre 0 e 1
        double randomValue = Math.random();

        // Se o valor for menor que 0.5, o jogador 1 começa, caso contrário, o jogador 2 começa
        String jogadorInicio = (randomValue < 0.5) ? jogador1Final : jogador2Final;

        // Retorna o jogador que começa
        return "O jogador que começará é: " + jogadorInicio;
    }
}