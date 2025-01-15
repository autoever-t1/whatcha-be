package com.example.whatcha.domain.websocket.handler;

import com.example.whatcha.domain.websocket.dto.ChatMessage;
import com.example.whatcha.domain.websocket.manager.WebSocketSessionManager;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();  // JSON 변환용 ObjectMapper

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            // 받은 메시지를 JSON에서 ChatMessage 객체로 변환
            ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);

            // 사용자 세션 관리: 세션 추가 (예시)
            if (chatMessage.getSender().equals("user")) {
                WebSocketSessionManager.addSession("user", session);
            }

            // 메시지 타입이 CHAT일 때만 관리자에게 메시지 전송
            if (chatMessage.getType() == ChatMessage.MessageType.CHAT) {
                WebSocketSession adminSession = WebSocketSessionManager.getAdminSession(); // 관리자의 세션을 얻어오는 메서드
                if (adminSession != null && adminSession.isOpen()) {
                    // 관리자가 연결되어 있으면 관리자에게 메시지 전송
                    adminSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessage)));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // 새 연결이 맺어졌을 때 세션 등록
        String userId = "userId"; // 실제 사용자 ID로 설정
        WebSocketSessionManager.addSession(userId, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus closeStatus) {
        // 연결이 종료되었을 때 세션 제거
        String userId = "userId"; // 실제 사용자 ID로 설정
        WebSocketSessionManager.removeSession(userId);
    }

    private String getUserId(WebSocketSession session) {
        // 예시로 사용자 ID를 session에서 추출하는 방법을 사용
        return (String) session.getAttributes().get("userId");
    }

    public void sendMessageToUser(String userId, ChatMessage chatMessage) {
        WebSocketSession userSession = WebSocketSessionManager.getSession(userId); // 사용자의 세션을 가져옴
        if (userSession != null && userSession.isOpen()) {
            try {
                // 사용자에게 메시지 전송
                userSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessage)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
