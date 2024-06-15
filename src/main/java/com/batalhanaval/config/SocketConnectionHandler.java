package com.batalhanaval.config;

import com.batalhanaval.controller.GameController;
import com.batalhanaval.dtos.GameModel;
import com.batalhanaval.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.CloseStatus;

import java.util.*;

public class SocketConnectionHandler extends TextWebSocketHandler {
    //https://medium.com/@parthiban.rajalingam/introduction-to-web-sockets-using-spring-boot-and-angular-b11e7363f051
    //https://www.geeksforgeeks.org/spring-boot-web-socket/
    public static Map<Long, WebSocketSession> webSocketSessions
            = new HashMap<>();

    private UserService userService;
    private GameController gameController;

    public SocketConnectionHandler(UserService userService, GameController gameController) {
        this.userService = userService;
        this.gameController = gameController;
    }

    @Override
    public void
    afterConnectionEstablished(WebSocketSession session)
            throws Exception
    {

        super.afterConnectionEstablished(session);
        // Logging the connection ID with Connected Message
        System.out.println(session.getId() + " Connected");
        System.out.println(this.userService.usuarioLogadoId);
        // Adding the session into the list
        webSocketSessions.remove(this.userService.usuarioLogadoId);
        webSocketSessions.put(this.userService.usuarioLogadoId, session);

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus status) throws Exception
    {
        super.afterConnectionClosed(session, status);
        System.out.println(session.getId()
                + " DisConnected");

        // Removing the connection info from the list
        webSocketSessions.remove(this.userService.usuarioLogadoId);
    }

    @Override
    public void handleMessage(WebSocketSession session,
                              WebSocketMessage<?> message)
            throws Exception
    {

        super.handleMessage(session, message);
        this.gameController.sendMessage(message);
    }
}
