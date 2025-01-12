package com.example.whatcha.domain.admin.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    @GetMapping("/order/stats/model")
    public ResponseEntity<?> getStatsByModel() {
        return null;
    }

    @GetMapping("/order/stats/age-groups")
    public ResponseEntity<?> getStatsByAge() {
        return null;
    }
}

