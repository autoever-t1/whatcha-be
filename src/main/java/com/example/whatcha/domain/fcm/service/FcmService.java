package com.example.whatcha.domain.fcm.service;

import java.io.IOException;

public interface FcmService {
    void sendMessageTo(String appToken, String title, String body) throws IOException;
}
