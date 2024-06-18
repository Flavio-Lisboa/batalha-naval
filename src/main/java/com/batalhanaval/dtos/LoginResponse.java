package com.batalhanaval.dtos;

import com.batalhanaval.entity.NivelAcesso;
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
    private NivelAcesso nivelAcesso;
}
