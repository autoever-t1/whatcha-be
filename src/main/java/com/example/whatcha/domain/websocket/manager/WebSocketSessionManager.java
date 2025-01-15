package com.example.whatcha.domain.websocket.manager;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketSessionManager {

    // 사용자 세션을 관리할 ConcurrentHashMap
    private static final ConcurrentHashMap<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();
    // 관리자 세션 가져오기
    // 관리자 세션 설정
    @Getter
    @Setter
    private static WebSocketSession adminSession;

    // 사용자 세션 추가
    public static void addSession(String userId, WebSocketSession session) {
        userSessions.put(userId, session);
    }

    // 사용자 세션 제거
    public static void removeSession(String userId) {
        userSessions.remove(userId);
    }

    // 사용자 세션 가져오기
    public static WebSocketSession getSession(String userId) {
        return userSessions.get(userId);
    }

}
