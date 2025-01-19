package com.example.whatcha.domain.fcm.service;

import com.example.whatcha.domain.fcm.domain.FcmMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmServiceImpl implements FcmService {

    @Value("${firebase.config.path}")
    private Resource firebaseConfigPath;

    private final ObjectMapper objectMapper;
    @Value("${firebase.fcm.api-url}")
    private String apiUrl;

    private String getAccessToken() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(firebaseConfigPath.getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();

        return googleCredentials.getAccessToken().getTokenValue();
    }

    private String makeMessage(String targetToken, String title, String body) throws
            com.fasterxml.jackson.core.JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build()
                        ).build()).validateOnly(false).build();
        return objectMapper.writeValueAsString(fcmMessage);
    }

@Override
public void sendMessageTo(String appToken, String title, String body) throws IOException {
    if (appToken == null || appToken.isEmpty()) {
        throw new IllegalArgumentException("FCM 토큰이 유효하지 않습니다.");
    }

    String message = makeMessage(appToken, title, body);
    OkHttpClient client = new OkHttpClient();
    RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
    Request request = new Request.Builder()
            .url(apiUrl)
            .post(requestBody)
            .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
            .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
            .build();

    Response response = client.newCall(request).execute();
    String responseBody = response.body().string();
    if (response.code() != 200) {
        log.error("푸시 알람 전송 실패: {}", responseBody);
        throw new IOException("푸시 알람 전송에 실패했습니다. 응답: " + responseBody);
    }

    System.out.println(responseBody);
}

}
