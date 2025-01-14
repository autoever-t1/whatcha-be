package com.example.whatcha.domain.usedCar.initializer;
import com.example.whatcha.domain.usedCar.domain.Color;
import com.example.whatcha.domain.usedCar.dao.ColorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ColorRepository colorRepository;

    public DataInitializer(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
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
