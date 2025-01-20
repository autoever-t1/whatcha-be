package com.example.whatcha.domain.fcm.service;

import com.example.whatcha.domain.fcm.domain.FcmMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.gson.JsonParser;
import com.google.gson.JsonParseException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class FcmServiceImpl implements FcmService {

    private final Resource firebaseConfigPath;
    private final String apiUrl;
    private final ObjectMapper objectMapper;
    private final OkHttpClient okHttpClient;

    public FcmServiceImpl(
            ObjectMapper objectMapper,
            @Value("classpath:worryboxFirebaseKey.json") Resource firebaseConfigPath,
            @Value("${firebase.fcm.api-url}") String apiUrl) {
        this.objectMapper = objectMapper;
        this.firebaseConfigPath = firebaseConfigPath;
        this.apiUrl = apiUrl;
        this.okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    private String getAccessToken() throws IOException {
        try (InputStream inputStream = firebaseConfigPath.getInputStream()) {
            if (inputStream == null) {
                throw new FileNotFoundException("Firebase 설정 파일을 찾을 수 없습니다.");
            }

            // JSON 파일 유효성 검사
            try (InputStreamReader reader = new InputStreamReader(inputStream)) {
                new JsonParser().parse(reader);
            } catch (JsonParseException e) {
                throw new IOException("Firebase 설정 파일이 올바른 JSON 형식이 아닙니다.", e);
            }

            // 새로운 스트림으로 GoogleCredentials 생성
            try (InputStream credentialStream = firebaseConfigPath.getInputStream()) {
                GoogleCredentials googleCredentials = GoogleCredentials
                        .fromStream(credentialStream)
                        .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

                googleCredentials.refreshIfExpired();
                return googleCredentials.getAccessToken().getTokenValue();
            }
        } catch (IOException e) {
            log.error("Firebase 인증 토큰 생성 실패: {}", e.getMessage(), e);
            throw new IOException("Firebase 인증 토큰 생성에 실패했습니다.", e);
        }
    }

    private String makeMessage(String appToken, String title, String body, String modelName, Integer price) throws JsonProcessingException {
        validateMessageParameters(appToken, title, body);

        try {
            FcmMessage fcmMessage = FcmMessage.builder()
                    .message(FcmMessage.Message.builder()
                            .token(appToken)
                            .notification(FcmMessage.Notification.builder()
                                    .title(title)
                                    .body(body)
                                    .image(null)
                                    .build())
                            .build())
                    .validateOnly(false)
                    .build();

            return objectMapper.writeValueAsString(fcmMessage);
        } catch (JsonProcessingException e) {
            log.error("FCM 메시지 생성 실패: {}", e.getMessage(), e);
            throw new JsonProcessingException("FCM 메시지 생성에 실패했습니다.") {};
        }
    }

    private void validateMessageParameters(String appToken, String title, String body) {
        if (appToken == null || appToken.trim().isEmpty()) {
            throw new IllegalArgumentException("FCM 토큰이 유효하지 않습니다.");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("알림 제목이 유효하지 않습니다.");
        }
        if (body == null || body.trim().isEmpty()) {
            throw new IllegalArgumentException("알림 내용이 유효하지 않습니다.");
        }
    }

    private Map<String, String> createDataPayload(String modelName, Integer price) {
        Map<String, String> data = new HashMap<>();
        if (modelName != null) {
            data.put("modelName", modelName);
        }
        if (price != null) {
            data.put("price", price.toString());
        }
        return data;
    }

    @Override
    public void sendMessageTo(String appToken, String title, String body, String modelName, Integer price) throws IOException {
        Response response = null;
        try {
            String message = makeMessage(appToken, title, body, modelName, price);
            Request request = createFcmRequest(message);

            response = okHttpClient.newCall(request).execute();
            handleFcmResponse(response, message);
        } catch (Exception e) {
            log.error("푸시 알람 전송 중 오류 발생: {}", e.getMessage(), e);
            throw new IOException("푸시 알람 전송 중 오류가 발생했습니다: " + e.getMessage(), e);
        } finally {
            if (response != null && response.body() != null) {
                response.body().close();
            }
        }
    }

    private Request createFcmRequest(String message) throws IOException {
        return new Request.Builder()
                .url(apiUrl)
                .post(RequestBody.create(message, MediaType.get("application/json; charset=utf-8")))
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();
    }

    private void handleFcmResponse(Response response, String message) throws IOException {
        String responseBody = response.body() != null ? response.body().string() : "";

        log.info("FCM Request message: {}", message);
        log.info("FCM Response status: {}", response.code());
        log.info("FCM Response body: {}", responseBody);

        if (!response.isSuccessful()) {
            throw new IOException(String.format("푸시 알람 전송 실패 (상태 코드: %d, 응답: %s)",
                    response.code(), responseBody));
        }

        log.info("푸시 알람 전송 성공");
    }
}