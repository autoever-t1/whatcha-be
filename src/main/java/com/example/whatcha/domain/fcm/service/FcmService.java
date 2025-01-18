package com.example.whatcha.domain.fcm.service;

import java.io.IOException;

public interface FcmService {
//    int sendMessage(String message) throws IOException;
    void sendMessageTo(String title, String body) throws IOException;
}
