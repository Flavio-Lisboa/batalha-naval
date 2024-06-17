package com.batalhanaval.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.CloseStatus;

import java.io.IOException;
import java.util.*;


public class SocketConnectionHandler extends TextWebSocketHandler {
    //https://medium.com/@parthiban.rajalingam/introduction-to-web-sockets-using-spring-boot-and-angular-b11e7363f051
    //https://www.geeksforgeeks.org/spring-boot-web-socket/
    List<WebSocketSession> webSocketSessions
            = Collections.synchronizedList(new ArrayList<>());


    private List<Map<String, String>> arr = new ArrayList<>();
    private List<Map<String, Map<String, String>>> playingArray = new ArrayList<>();
    private Map<String, String> sessionIdMap = new HashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        Map<String, String> infosUser = new HashMap<>();
        Map<String, String> player = new ObjectMapper().readValue(payload, HashMap.class);
        //System.out.println("entrei");
        //System.out.println(payload);
        if (player.get("evento").equals("find")) {
            if(player.get("userId") != null){

                for (int i = 0; i < arr.size(); i++) {
                    Map<String, String> map = arr.get(i);
                    String userId = map.get("userId");

                    if (userId.equals(player.get("userId"))){
                        arr.remove(i);
                        i--;
                    }
                }

                /*arr.add(session.getId());
                arr.add(player.get("userId"));
                arr.add(player.get("tabuleiro"));*/
                infosUser.put("sessionId", session.getId());
                infosUser.put("userId", player.get("userId"));
                infosUser.put("tabuleiro", player.get("tabuleiro"));
                infosUser.put("navios", player.get("navios"));


                arr.add(infosUser);

                if (arr.size() >= 2) {

                    Random random = new Random();
                    int p1PodeJogar = random.nextInt(2);

                    if(p1PodeJogar == 1){
                        arr.get(0).put("podeJogar", "true");
                        arr.get(1).put("podeJogar", "false");
                    }
                    else{
                        arr.get(1).put("podeJogar", "true");
                        arr.get(0).put("podeJogar", "false");
                    }

                    Map<String, Map<String, String>> pair = new HashMap<>();
                    pair.put("p1", arr.get(0));
                    pair.put("p2", arr.get(1));

                    playingArray.add(pair);

                    arr.remove(0);
                    arr.remove(0);

                    String responseString = new ObjectMapper().writeValueAsString(pair);

                    this.sendMessageToClient(pair.get("p1").get("sessionId").toString(), "find", responseString);
                    this.sendMessageToClient(pair.get("p2").get("sessionId").toString(), "find", responseString);
                }

            }
        }
        else if (player.get("evento").equals("jogando")){

            Map<String, Map<String, Map>> msg = new ObjectMapper().readValue(payload, HashMap.class);
            String responseMsg = new ObjectMapper().writeValueAsString(msg);

            this.sendMessageToClient(msg.get("players").get("p1").get("sessionId").toString(), "jogando", responseMsg);
            this.sendMessageToClient(msg.get("players").get("p2").get("sessionId").toString(), "jogando", responseMsg);

        }
        else if (player.get("evento").equals("emoji")){
            Map<String, Object> msg = new ObjectMapper().readValue(payload, HashMap.class);
            Map<String, Map<String, Object>> players = (Map<String, Map<String, Object>>) msg.get("players");

            if (session.getId().equals(players.get("p1").get("sessionId").toString())){
                this.sendMessageToClient(players.get("p2").get("sessionId").toString(), "emoji", msg.get("emoji").toString());
            }
            else{
                this.sendMessageToClient(players.get("p1").get("sessionId").toString(), "emoji", msg.get("emoji").toString());
            }

        }
        else if (player.get("evento").equals("final")){
            Map<String, Object> msg = new ObjectMapper().readValue(payload, HashMap.class);
            Map<String, Map<String, Object>> players = (Map<String, Map<String, Object>>) msg.get("players");

            String responseMsg = new ObjectMapper().writeValueAsString(msg);


            if (session.getId().equals(players.get("p1").get("sessionId").toString())){
                //p1 ganha
                //this.userController.resultadoJogo( (boolean) msg.get("win"), Long.parseLong(players.get("p1").get("userId").toString()));
                //this.userController.resultadoJogo( !(boolean) msg.get("win"), Long.parseLong(players.get("p2").get("userId").toString()));

                this.sendMessageToClient(players.get("p2").get("sessionId").toString(), "final", responseMsg);
            }
            else{
                //this.userController.resultadoJogo( !(boolean) msg.get("win"), Long.parseLong(players.get("p1").get("userId").toString()));
                //this.userController.resultadoJogo( (boolean) msg.get("win"), Long.parseLong(players.get("p2").get("userId").toString()));
                this.sendMessageToClient(players.get("p1").get("sessionId").toString(), "final", responseMsg);
            }
        }
    }

    public void sendMessageToAllClients(String message) {
        for (WebSocketSession session : webSocketSessions) {
            try {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(message));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para enviar uma mensagem para um cliente específico
    public void sendMessageToClient(String sessionId, String event, String msg) throws Exception {
        WebSocketSession session = findSessionById(sessionId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", msg);
        response.put("evento", event);

        String responseString = new ObjectMapper().writeValueAsString(response);

        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(responseString));
        }
    }

    // Método para encontrar uma sessão pelo ID
    private WebSocketSession findSessionById(String sessionId) {
        for (WebSocketSession session : webSocketSessions) {
            if (session.getId().equals(sessionId)) {
                return session;
            }
        }
        return null;
    }


    @Override
    public void
    afterConnectionEstablished(WebSocketSession session)
            throws Exception
    {

        super.afterConnectionEstablished(session);
        // Logging the connection ID with Connected Message
        System.out.println(session.getId() + " Connected");

        // Adding the session into the list
        webSocketSessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus status) throws Exception
    {
        super.afterConnectionClosed(session, status);
        System.out.println(session.getId()
                + " DisConnected");


        for (int i = 0; i < arr.size(); i++) {
            Map<String, String> map = arr.get(i);
            String sessao = map.get("sessionId");

            if (sessao.equals(session.getId())){
                arr.remove(i);
                i--;
            }
        }

        for (int i = 0; i < playingArray.size(); i++) {
            Map<String, Map<String, String>> map = playingArray.get(i);
            Map<String, String> p1 = map.get("p1");
            Map<String, String> p2 = map.get("p2");

            if (p1.get("sessionId").equals(session.getId())){
                this.sendMessageToClient(p2.get("sessionId"), "closed", "Usuario x se desconectou");
                playingArray.remove(i);
                i--;
            } else if (p2.get("sessionId").equals(session.getId())) {
                this.sendMessageToClient(p1.get("sessionId"), "closed", "Usuario x se desconectou");

                playingArray.remove(i);
                i--; // Decrementa o contador para compensar a remoção do elemento
            }
        }

        // Exibindo a lista atualizada
        System.out.println(playingArray);

        // Removing the connection info from the list
        webSocketSessions.remove(session);
    }

}
