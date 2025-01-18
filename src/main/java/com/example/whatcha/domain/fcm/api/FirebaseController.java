package com.example.whatcha.domain.fcm.api;

import com.example.whatcha.domain.fcm.domain.ApiResponse;
import com.example.whatcha.domain.fcm.dto.FcmRequestDto;
import com.example.whatcha.domain.fcm.service.FcmServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fcm")
public class FirebaseController {

    private final FcmServiceImpl fcmService;

    @PostMapping("/pushMessage")
    public ApiResponse<String> pushMessage(@RequestBody FcmRequestDto requestDto) {
        try {
            System.out.println(requestDto.getDeviceToken() + " "
                    + requestDto.getTitle() + " " + requestDto.getBody());

            // FCM 메시지 전송 시도
            fcmService.sendMessageTo(
                    requestDto.getDeviceToken(),
                    requestDto.getTitle(),
                    requestDto.getBody());

            // 성공적으로 메시지를 보낸 경우
            return ApiResponse.onSuccess("FCM_SEND_SUCCESS", "fcm alarm success");

        } catch (IOException e) {
            // FCM 메시지 전송 중 오류 발생 시 처리
            return ApiResponse.onFailure("FCM message send failed due to IO issue: " + e.getMessage());
        } catch (Exception e) {
            // 그 외의 오류 처리
            return ApiResponse.onFailure( "FCM message send failed: " + e.getMessage());
        }
    }
}
