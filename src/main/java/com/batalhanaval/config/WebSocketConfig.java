package com.batalhanaval.config;

import com.batalhanaval.controller.GameController;
import com.batalhanaval.service.UserService;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private UserService userService;
    private GameController gameController;

    public WebSocketConfig(UserService userService, GameController gameController) {
        this.userService = userService;
        this.gameController = gameController;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SocketConnectionHandler(this.userService, this.gameController), "/game").setAllowedOrigins("*");
    }

}