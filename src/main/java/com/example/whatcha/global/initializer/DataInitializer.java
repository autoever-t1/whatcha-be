package com.example.whatcha.global.initializer;

import com.example.whatcha.domain.branchStore.dao.BranchStoreRepository;
import com.example.whatcha.domain.branchStore.domain.BranchStore;
import com.example.whatcha.domain.usedCar.dao.ColorRepository;
import com.example.whatcha.domain.usedCar.domain.Color;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BranchStoreRepository branchStoreRepository;
    private final ColorRepository colorRepository;

    public DataInitializer(BranchStoreRepository branchStoreRepository, ColorRepository colorRepository) {
        this.branchStoreRepository = branchStoreRepository;
        this.colorRepository = colorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 데이터가 없으면만 실행
        if (branchStoreRepository.count() == 0) {
            branchStoreRepository.save(new BranchStore(
                    1L,
                    "현대자동차 인증중고차센터 용인",
                    "경기 용인시 기흥구 중부대로 242",
                    37.269068,
                    127.092517,
                    0,
                    "1522-0880"
            ));
            branchStoreRepository.save(new BranchStore(
                    2L,
                    "현대자동차 인증중고차센터 양산",
                    "경남 양산시 하북면 삼동로 44",
                    35.497006,
                    129.095554,
                    0,
                    "1522-0880"
            ));
            branchStoreRepository.save(new BranchStore(
                    3L,
                    "현대자동차 인증중고차센터 군산",
                    "전북 군산시 모갱이1길 62",
                    35.984825,
                    126.765955,
                    0,
                    "1522-0880"
            ));
        }

        if (colorRepository.count() == 0) {
            colorRepository.save(new Color(1L, "화이트"));
            colorRepository.save(new Color(2L, "베이지"));
            colorRepository.save(new Color(3L, "실버"));
            colorRepository.save(new Color(4L, "그레이"));
            colorRepository.save(new Color(5L, "블랙"));
            colorRepository.save(new Color(6L, "브라운"));
            colorRepository.save(new Color(7L, "레드"));
            colorRepository.save(new Color(8L, "오렌지"));
            colorRepository.save(new Color(9L, "옐로우"));
            colorRepository.save(new Color(10L, "그린"));
            colorRepository.save(new Color(11L, "블루"));
            colorRepository.save(new Color(12L, "퍼플"));
        }
    }
}
