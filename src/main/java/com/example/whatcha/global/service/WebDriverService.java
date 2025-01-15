package com.example.whatcha.global.service;

import com.example.whatcha.domain.branchStore.dao.BranchStoreRepository;
import com.example.whatcha.domain.branchStore.domain.BranchStore;
import com.example.whatcha.domain.branchStore.exception.BranchStoreNotFoundException;
import com.example.whatcha.domain.usedCar.dao.*;
import com.example.whatcha.domain.usedCar.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.*;

import static com.example.whatcha.domain.branchStore.constant.BranchStoreExceptionMessage.BRANCH_STORE_NOT_FOUND;

@Component
@Slf4j
public class WebDriverService {
    private final ModelRepository modelRepository;
    private final BranchStoreRepository branchStoreRepository;
    private final ColorRepository colorRepository;
    private final UsedCarRepository usedCarRepository;
    private final UsedCarImageRepository usedCarImageRepository;
    private final OptionRepository optionRepository;
    private WebDriver driver;
    private static final String base_url = "https://certified.hyundai.com/p/search/vehicle";
    private static final String detail_url = "https://certified.hyundai.com/p/goods/goodsDetail.do?goodsNo=";

    public WebDriverService(ModelRepository modelRepository, BranchStoreRepository branchStoreRepository, ColorRepository colorRepository, UsedCarRepository usedCarRepository, UsedCarImageRepository usedCarImageRepository, OptionRepository optionRepository) {
        this.modelRepository = modelRepository;
        this.branchStoreRepository = branchStoreRepository;
        this.colorRepository = colorRepository;
        this.usedCarRepository = usedCarRepository;
        this.usedCarImageRepository = usedCarImageRepository;
        this.optionRepository = optionRepository;
    }

    //"/Users/user/leejoohee/Desktop/chromedriver-mac-x64/chromedriver"
    @PostConstruct
    public void init() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\Desktop\\chromedriver-win64\\chromedriver.exe");
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
        }
    }

    public void getCarListAndDetails() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            driver.get(base_url);

            // 중복 제거를 위한 Set 사용
            Set<String> carNumbersSet = new HashSet<>();

            // 전체 데이터 갯수 추출
            WebElement totalCountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("em#saleVehicleTotalCount")));
            int totalCount = Integer.parseInt(totalCountElement.getText().replaceAll("[^0-9]", ""));
            //int totalCount = 30;
            log.info("총 차량 갯수: " + totalCount);

            // 데이터가 목표 갯수에 도달할 때까지 "더보기" 버튼 반복 클릭
            while (carNumbersSet.size() < totalCount) {
                // 차량 목록에서 모든 input[data-favcontsno] 값 추출
                List<WebElement> carItems = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("input[data-favcontsno]")));

                // 차량 정보 추출 및 중복 제거
                for (WebElement item : carItems) {
                    String carNumber = item.getAttribute("data-favcontsno");
                    carNumbersSet.add(carNumber);
                }

                log.info("현재 가져온 데이터: " + carNumbersSet.size() + " / " + totalCount);

                // 목표 데이터를 모두 가져왔으면 중단
                if (carNumbersSet.size() >= totalCount) {
                    break;
                }

                // "더보기" 버튼 클릭
                try {
                    WebElement seeMoreButton = driver.findElement(By.id("btnSeeMore"));
                    if (seeMoreButton.isDisplayed()) {
                        log.info("더보기 클릭중 ..");
                        seeMoreButton.click();
                        // 데이터 로드 대기
                        Thread.sleep(2000); // 네트워크 요청 시간 대기
                    } else {
                        log.warn("'더보기' 버튼이 더 이상 표시되지 않습니다.");
                        break;
                    }
                } catch (NoSuchElementException e) {
                    log.warn("'더보기' 버튼을 찾을 수 없습니다.");
                    break;
                }
            }

            // 중복 제거된 차량 번호 출력 및 상세 정보 수집
            for (String carNumber : carNumbersSet) {

                getCarDetails(carNumber);

                // 새로운 페이지로 이동하기 전에 잠깐 대기 시간을 주어 페이지가 완전히 로드되도록 처리
                Thread.sleep(2000);
            }

        } catch (Exception e) {
            log.error("차량 리스트 및 상세 정보 가져오기 중 오류 발생", e);
        }
    }

    public void getCarDetails(String carNumber) {
        String vehicleUrl = detail_url + carNumber;

        // 변수 초기화
        String modelName = "정보 없음";
        String colorName = "정보 없음";
        String branchStoreName = "정보 없음";
        Integer price = 0;
        String registrationDate = "정보 없음";
        String mileage = "정보 없음";
        String fuelType = "정보 없음";
        Double engineCapacity = 0.0;
        String exteriorColor = "정보 없음";
        String interiorColor = "정보 없음";
        String modelType = "정보 없음";
        Integer passengerCapacity = 0;
        String driveType = "정보 없음";
        String vhclRegNo = "정보 없음";
        String years = "정보 없음";
        String transmission = "정보 없음";
        String status = "구매 가능";
        String goodsNo = carNumber;
        String mainImage = "https://certified-static.hyundai.com/contents/goods/shootConts/tobepic/02/exterior/" + carNumber + "/PRD602_233.JPG/dims/crop/2304x1536+600+770/resize/380x253/optimize";
        Integer factoryPrice = 0;

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            driver.get(vehicleUrl);

            // 차량 정보 크롤링
            WebElement nameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.name")));
            modelName = nameElement.getText();

            WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.txt.pay")));
            price = Integer.parseInt(priceElement.getText().replaceAll("[^0-9]", "")) * 10000;

            WebElement factoryPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.num")));
            String factoryPriceText = factoryPriceElement.getText().replace(",", ""); // 쉼표 제거
            factoryPrice = Integer.parseInt(factoryPriceText);

            WebElement branchStoreElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.info li span.text")));
            String branchStoreText = branchStoreElement.getText();
            String cityName = extractCityName(branchStoreText);


            BranchStore branchStore = branchStoreRepository.findByBranchStoreNameContaining(cityName)
                    .orElseThrow(() -> new BranchStoreNotFoundException(BRANCH_STORE_NOT_FOUND.getMessage()));

            branchStore.incrementOwnedCarCount();

            branchStoreRepository.save(branchStore);

            WebElement detailBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ol.base_01")));
            List<WebElement> detailList = detailBox.findElements(By.tagName("li"));

            for (WebElement detail : detailList) {
                WebElement titleElement = detail.findElement(By.className("tit"));
                WebElement valueElement = detail.findElement(By.className("txt"));
                String title = titleElement.getText();
                String value = valueElement.getText();

                // 각 항목에 맞는 값을 매핑
                switch (title) {
                    case "최초등록":
                        registrationDate = value;
                        break;
                    case "주행거리":
                        mileage = value.replaceAll("[^0-9]", ""); // 숫자만 추출
                        break;
                    case "연료":
                        fuelType = value;
                        break;
                    case "배기량":
                        engineCapacity = value.replaceAll("[^0-9.]", "").isEmpty() ? 0.0 : Double.valueOf(value.replaceAll("[^0-9.]", ""));
                        break;
                    case "외관컬러":
                        exteriorColor = value;
                        break;
                    case "내장컬러":
                        interiorColor = value;
                        break;
                    case "차종":
                        modelType = value;
                        break;
                    case "승차인원":
                        passengerCapacity = Integer.parseInt(value.replaceAll("[^0-9]", ""));
                        break;
                    case "구동방식":
                        driveType = value;
                        break;
                    case "차량번호":
                        vhclRegNo = value;
                        break;
                    case "연식":
                        years = value.replaceAll("[^0-9]", "");
                        break;
                    case "변속기":
                        transmission = value;
                        break;
                    default:
                        break;
                }
            }

            // 모델 정보 처리
            Model model = modelRepository.findByModelName(modelName).orElse(null);
            if (model == null) {
                model = Model.builder()
                        .modelName(modelName)
                        .modelType(modelType)
                        .factoryPrice(factoryPrice)
                        .orderCount(0)
                        .build();
                modelRepository.save(model);
            }

            List<String> colorList = Arrays.asList("화이트", "베이지", "실버", "그레이", "블랙", "브라운", "레드", "오렌지", "옐로우", "그린", "블루", "퍼플");
            for (String color : colorList) {
                if (exteriorColor.contains(color)) {
                    colorName = color;
                    break;
                }
            }

            Color color = colorRepository.findByColorName(colorName).orElse(null);
            log.info("차량 색상: " + colorName);

            UsedCar usedCar = UsedCar.builder()
                    .model(model)
                    .color(color)
                    .branchStore(branchStore)
                    .registrationDate(registrationDate)
                    .vhclRegNo(vhclRegNo)
                    .modelName(modelName)
                    .modelType(modelType)
                    .fuelType(fuelType)
                    .mileage(mileage)
                    .exteriorColor(exteriorColor)
                    .interiorColor(interiorColor)
                    .price(price)
                    .status(status)
                    .years(years)
                    .engineCapacity(engineCapacity)
                    .passengerCapacity(passengerCapacity)
                    .driveType(driveType)
                    .transmission(transmission)
                    .goodsNo(goodsNo)
                    .mainImage(mainImage)
                    .build();

            usedCarRepository.save(usedCar);

            List<UsedCarImage> images = new ArrayList<>();

            List<WebElement> imageElements = driver.findElements(By.cssSelector("button.item img"));

            for (int i = 1; i <= imageElements.size(); i++) {

                String imageUrl = "https://certified-static.hyundai.com/contents/goods/shootConts/tobepic/02/usp/" + carNumber + "/PRD602_300_" + i + ".JPG/dims/optimize";

                UsedCarImage usedCarImage = UsedCarImage.builder()
                        .image(imageUrl)
                        .usedCar(usedCar)
                        .build();

                // 이미지 리스트에 추가
                images.add(usedCarImage);
            }

            usedCarImageRepository.saveAll(images);


            WebElement optionsBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ol.option_01")));
            List<WebElement> optionList = optionsBox.findElements(By.tagName("li"));

            Option option = parseOptions(optionList, usedCar);
            optionRepository.save(option);

        } catch (NoSuchElementException e) {
            log.error("페이지 찾을 수 없음: " + vehicleUrl, e);
        } catch (Exception e) {
            log.error("상세 페이지 찾을 수 없음: " + vehicleUrl, e);
        }
    }


    private String extractCityName(String branchStoreText) {
        // "인증중고차센터"는 제외하고 첫 번째 단어만 추출
        String[] parts = branchStoreText.split(" ");
        if (parts.length > 0) {
            String cityName = parts[0]; // 첫 번째 단어
            if (cityName.equalsIgnoreCase("인증중고차센터")) {
                return null; // "인증중고차센터"는 제외
            }
            return cityName; // 도시 이름 반환
        }
        return null; // 유효한 도시 이름이 없을 경우 null 반환
    }

    private Option parseOptions(List<WebElement> optionList, UsedCar usedCar) {
        boolean hasNavigation = true;
        boolean hasHiPass = true;
        boolean hasHeatedSteeringWheel = true;
        boolean hasHeatedSeats = true;
        boolean hasVentilatedSeats = true;
        boolean hasPowerSeats = true;
        boolean hasLeatherSeats = true;
        boolean hasPowerTrunk = true;
        boolean hasSunroof = true;
        boolean hasHUD = true;
        boolean hasSurroundViewMonitor = true;
        boolean hasRearMonitor = true;
        boolean hasBlindSpotWarning = true;
        boolean hasLaneDepartureWarning = true;
        boolean hasSmartCruiseControl = true;
        boolean hasFrontParkingWarning = true;

        // 옵션 목록을 순회하며 각 옵션에 대한 상태 설정
        for (WebElement option : optionList) {
            String optionName = option.getText().trim();
            boolean isActive = !option.getAttribute("class").contains("off");  // "off" 클래스가 있으면 false, 없으면 true

            switch (optionName) {
                case "내비게이션":
                    hasNavigation = isActive;
                    break;
                case "하이패스":
                    hasHiPass = isActive;
                    break;
                case "열선 스티어링 휠":
                    hasHeatedSteeringWheel = isActive;
                    break;
                case "열선시트(1열/2열)":
                    hasHeatedSeats = isActive;
                    break;
                case "통풍시트(1열)":
                    hasVentilatedSeats = isActive;
                    break;
                case "전동시트(1열)":
                    hasPowerSeats = isActive;
                    break;
                case "가죽 시트":
                    hasLeatherSeats = isActive;
                    break;
                case "전동식 트렁크":
                    hasPowerTrunk = isActive;
                    break;
                case "선루프":
                    hasSunroof = isActive;
                    break;
                case "헤드업 디스플레이":
                    hasHUD = isActive;
                    break;
                case "서라운드 뷰 모니터":
                    hasSurroundViewMonitor = isActive;
                    break;
                case "후방 모니터":
                    hasRearMonitor = isActive;
                    break;
                case "후측방 경보 시스템":
                    hasBlindSpotWarning = isActive;
                    break;
                case "차선 이탈 경보":
                    hasLaneDepartureWarning = isActive;
                    break;
                case "스마트 크루즈 컨트롤":
                    hasSmartCruiseControl = isActive;
                    break;
                case "전방 주차거리 경고":
                    hasFrontParkingWarning = isActive;
                    break;
                default:
                    break;
            }
        }

        return Option.builder()
                .usedCar(usedCar)
                .hasNavigation(hasNavigation)
                .hasHiPass(hasHiPass)
                .hasHeatedSteeringWheel(hasHeatedSteeringWheel)
                .hasHeatedSeats(hasHeatedSeats)
                .hasVentilatedSeats(hasVentilatedSeats)
                .hasPowerSeats(hasPowerSeats)
                .hasLeatherSeats(hasLeatherSeats)
                .hasPowerTrunk(hasPowerTrunk)
                .hasSunroof(hasSunroof)
                .hasHUD(hasHUD)
                .hasSurroundViewMonitor(hasSurroundViewMonitor)
                .hasRearMonitor(hasRearMonitor)
                .hasBlindSpotWarning(hasBlindSpotWarning)
                .hasLaneDepartureWarning(hasLaneDepartureWarning)
                .hasSmartCruiseControl(hasSmartCruiseControl)
                .hasFrontParkingWarning(hasFrontParkingWarning)
                .build();
    }
}
