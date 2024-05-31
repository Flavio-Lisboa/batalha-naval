package com.batalhanaval.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String mensagem;
    private Boolean sucessoLogin;
    private Long usuarioId;
    private String nomeUsuario;
}
