package com.batalhanaval.controller;

import com.batalhanaval.config.SocketConnectionHandler;
import com.batalhanaval.dtos.GameModel;
import com.batalhanaval.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(message.getPayload().toString());
        Long usuarioId = jsonNode.path("usuarioId").asLong();

        Long oponenteId = this.playerConnections.get(usuarioId);

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

    @GetMapping("pode-jogar/usuarios/{usuarioId}")
    @CrossOrigin
    public GameModel conectarComOutroJogador(@PathVariable Long usuarioId) {

        GameModel gameModel = new GameModel();

        if (usuariosEmEspera.size() > 1) {
            for (int i = 0; i < usuariosEmEspera.size(); i++) {

                if (!Objects.equals(usuariosEmEspera.get(i), usuarioId)) {
                    this.playerConnections.put(usuarioId, usuariosEmEspera.get(i));
                    this.playerConnections.put(usuariosEmEspera.get(i), usuarioId);
                    usuariosEmEspera.remove(usuariosEmEspera.get(i));
                    usuariosEmEspera.remove(usuarioId);

                    break;
                }
            }
        }

        System.out.println(this.playerConnections);

        if (this.playerConnections.containsKey(this.usuarioLogadoId) && this.playerConnections.containsValue(this.usuarioLogadoId)) {
            gameModel.setSucesso(true);
            gameModel.setOponenteId(this.playerConnections.get(usuarioId));
            return gameModel;
        }

        gameModel.setSucesso(false);
        return gameModel;
    }
}
