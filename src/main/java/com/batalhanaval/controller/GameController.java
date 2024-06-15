package com.batalhanaval.controller;

import com.batalhanaval.config.SocketConnectionHandler;
import com.batalhanaval.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("game")
public class GameController {

    private static final List<Long> usuariosEmEspera = new ArrayList<>();
    private Map<Long, Long> playerConnections = new HashMap<>();
    private Long usuarioLogadoId;

    private UserService userService;

    public GameController(UserService userService) {
        this.userService = userService;
    }

    public void sendMessage(WebSocketMessage<?> message) throws IOException {
        Long oponenteId = this.playerConnections.get(this.usuarioLogadoId);

        System.out.println(SocketConnectionHandler.webSocketSessions);
        WebSocketSession session = SocketConnectionHandler.webSocketSessions.get(oponenteId);
        session.sendMessage(message);
    }

    @GetMapping("em-espera/usuarios/{usuarioId}")
    @CrossOrigin
    public void emEspera(@PathVariable Long usuarioId) {
        this.usuarioLogadoId = usuarioId;
        usuariosEmEspera.remove(this.usuarioLogadoId);
        usuariosEmEspera.add(this.usuarioLogadoId);
    }

    @GetMapping("pode-jogar")
    @CrossOrigin
    public boolean conectarComOutroJogador() {
        System.out.println(usuariosEmEspera);

        if (usuariosEmEspera.size() > 1) {
            for (int i = 0; i < usuariosEmEspera.size(); i++) {

                if (!Objects.equals(usuariosEmEspera.get(i), this.usuarioLogadoId)) {
                    this.playerConnections.put(this.usuarioLogadoId, usuariosEmEspera.get(i));
                    this.playerConnections.put(usuariosEmEspera.get(i), this.usuarioLogadoId);

                    usuariosEmEspera.remove(usuariosEmEspera.get(i));
                    usuariosEmEspera.remove(this.usuarioLogadoId);

                    break;
                }
            }
        }

        System.out.println(this.playerConnections);

        if (this.playerConnections.containsKey(this.usuarioLogadoId) && this.playerConnections.containsValue(this.usuarioLogadoId)) {
            return true;
        }

        return false;
    }
}
