package com.example.whatcha.domain.fcm.service;

import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class FcmServiceImpl implements FcmService {

    @Value("${firebase.config.path}")
    private Resource firebaseConfigPath;

    @Value("${firebase.fcm.api-url}")
    private String apiUrl;

    private String getAccessToken() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(firebaseConfigPath.getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/firebase.messaging"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

    private String makeMessage(String targetToken, String title, String body) throws com.fasterxml.jackson.core.JsonProcessingException {
        FcmMessage
    }

    public void sendMessageTo(String title, String body) throws IOException {
        String message = makeMessage
    }

//    @Override
//    public int sendMessage(String message) throws IOException {
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "Bearer " + getAccessToken());
//
//        HttpEntity<String> entity = new HttpEntity<>(message, headers);
//        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);
//
//        System.out.println("Response Status: " + response.getStatusCode());
//        System.out.println("Response Body: " + response.getBody());
//
//        return response.getStatusCode() == HttpStatus.OK ? 1 : 0;
//    }


}
