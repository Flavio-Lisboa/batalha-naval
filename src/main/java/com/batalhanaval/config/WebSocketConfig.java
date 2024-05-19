package com.batalhanaval.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(tradeWebSocketHandler(), "/teste").setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler tradeWebSocketHandler() {
        return new TradeWebSocketHandler();
    }
}

class TradeWebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        for (int i=0; i < 100; i ++) {
            Teste teste = new Teste();
            teste.texto = "KKKKKKKKKKKK";

            TextMessage message = new TextMessage(objectMapper.writeValueAsString(teste));
            session.sendMessage(message);
            Thread.sleep(1000);
        }
        sessions.add(session);
    }
}

class Teste {
    public String texto;
}