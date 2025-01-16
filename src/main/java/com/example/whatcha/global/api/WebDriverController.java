//package com.example.whatcha.global.api;
//
//import com.example.whatcha.global.service.WebDriverService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RequiredArgsConstructor
//@RequestMapping("/crawling")
//@RestController
//public class WebDriverController {
//
//    private final WebDriverService webDriverService;
//
//    @GetMapping()
//    public String startCrawling() {
//            webDriverService.init(); // 드라이버 초기화
//            webDriverService.getCarListAndDetails(); // 크롤링 시작
//            webDriverService.close(); // 드라이버 종료
//            return "크롤링 성공";
//    }
//}