package com.example.whatcha.global.service;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Component
@Slf4j
public class WebDriverService {
    private WebDriver driver;

    @PostConstruct
    public void init() {
        System.setProperty("webdriver.chrome.driver", "/Users/leejoohee/Desktop/chromedriver-mac-x64/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking"); // 팝업 안 띄움
        options.addArguments("--disable-gpu"); // GPU 비활성화
        options.addArguments("--blink-settings=imagesEnabled=false"); // 이미지 다운 안 받음
        options.addArguments("--headless"); // 헤드리스 모드
        options.addArguments("--remote-allow-origins=*"); // 원격 접근 허용

        driver = new ChromeDriver(options);
    }

    @PreDestroy
    public void close() {
        if (driver != null) {
            driver.close(); // 탭 닫기
            driver.quit(); // 브라우저 닫기
            log.info("ChromeDriver closed successfully");
        }
    }

    public void getCarListAndDetails() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            driver.get("https://certified.hyundai.com/p/search/vehicle");

            // 차량 목록에서 모든 input[data-favcontsno] 값을 추출하고 해당 상세 링크로 이동
            List<WebElement> carItems = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("input[data-favcontsno]")));

            // 중복 제거를 위한 Set 사용
            Set<String> carNumbersSet = new HashSet<>();

            // 차량 정보 리스트
            for (WebElement item : carItems) {
                // data-favcontsno 값 추출
                String carNumber = item.getAttribute("data-favcontsno");

                // Set에 추가하여 중복을 자동으로 제거
                carNumbersSet.add(carNumber);
            }

            // 중복 제거된 차량 번호 출력
            for (String carNumber : carNumbersSet) {
                log.info("Car Number (data-favcontsno): " + carNumber);

                // 상세 페이지 URL 구성
                String vehicleUrl = "https://certified.hyundai.com/p/goods/goodsDetail.do?goodsNo=" + carNumber;
                log.info("Vehicle URL: " + vehicleUrl);

                // 각 차량의 상세 페이지에서 정보 추출
                String carDetails = getCarDetails(vehicleUrl);
                log.info("Car details:\n" + carDetails);

                // 새로운 페이지로 이동하기 전에 잠깐 대기 시간을 주어 페이지가 완전히 로드되도록 처리
                Thread.sleep(2000);
            }

        } catch (Exception e) {
            log.error("Error fetching car list and details", e);
        }
    }



    public String getCarDetails(String vehicleUrl) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            driver.get(vehicleUrl);

            // 차량 이름 크롤링
            WebElement nameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.name")));
            String carName = nameElement.getText();

            // 가격 크롤링
            WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.txt.pay")));
            String price = priceElement.getText();

            price = price.replaceAll("[^0-9]", "");

            // 차량 상세 정보 크롤링
            WebElement detailBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ol.base_01")));
            List<WebElement> detailList = detailBox.findElements(By.tagName("li"));

            StringBuilder details = new StringBuilder();
            for (WebElement detail : detailList) {
                try {
                    WebElement titleElement = detail.findElement(By.className("tit"));
                    WebElement valueElement = detail.findElement(By.className("txt"));
                    String title = titleElement.getText();
                    String value = valueElement.getText();
                    details.append(title).append(": ").append(value).append("\n");
                } catch (NoSuchElementException e) {
                    log.warn("No such element in detail list item: " + detail, e);
                }
            }

            // 차량 옵션 크롤링
            WebElement optionsBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ol.option_01")));
            List<WebElement> optionList = optionsBox.findElements(By.tagName("li"));

            StringBuilder options = new StringBuilder();
            for (WebElement option : optionList) {
                try {
                    String optionName = option.findElement(By.tagName("span")).getText();
                    options.append(optionName).append("\n");
                } catch (NoSuchElementException e) {
                    log.warn("No such element in option list item: " + option, e);
                }
            }

            // 데이터 출력
            return String.format("Car Name: %s\nPrice: %s\nDetails:\n%s\nOptions:\n%s",
                    carName, price, details.toString(), options.toString());
        } catch (NoSuchElementException e) {
            log.error("Element not found on the page: " + vehicleUrl, e);
            return "Error fetching car details (element not found).";
        } catch (Exception e) {
            log.error("Error fetching car details from URL: " + vehicleUrl, e);
            return "Error fetching car details.";
        }
    }
}
