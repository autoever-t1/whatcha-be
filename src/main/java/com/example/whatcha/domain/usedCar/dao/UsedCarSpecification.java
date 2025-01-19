package com.example.whatcha.domain.usedCar.dao;

import com.example.whatcha.domain.usedCar.domain.UsedCar;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UsedCarSpecification {

    public Specification<UsedCar> buildSpecification(
            List<Long> colorIds,
            List<String> modelTypes,
            List<String> modelNames,
            Integer mileageMin,
            Integer mileageMax,
            Integer yearMin,
            Integer yearMax,
            List<String> fuelTypes,
            Boolean hasNavigation,
            Boolean hasHiPass,
            Boolean hasHeatedSteeringWheel,
            Boolean hasHeatedSeats,
            Boolean hasVentilatedSeats,
            Boolean hasPowerSeats,
            Boolean hasLeatherSeats,
            Boolean hasPowerTrunk,
            Boolean hasSunroof,
            Boolean hasHUD,
            Boolean hasSurroundViewMonitor,
            Boolean hasRearMonitor,
            Boolean hasBlindSpotWarning,
            Boolean hasLaneDepartureWarning,
            Boolean hasSmartCruiseControl,
            Boolean hasFrontParkingWarning,
            Integer priceMin,
            Integer priceMax) {

        return (Root<UsedCar> root, javax.persistence.criteria.CriteriaQuery<?> query, javax.persistence.criteria.CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 색상 필터링
            if (colorIds != null && !colorIds.isEmpty()) {
                predicates.add(root.get("color").get("colorId").in(colorIds));
            }

            // 모델 타입 필터링
            if (modelTypes != null && !modelTypes.isEmpty()) {
                List<Predicate> modelTypePredicates = new ArrayList<>();
                for (String modelType : modelTypes) {
                    modelTypePredicates.add(criteriaBuilder.like(root.get("modelType"), "%" + modelType + "%"));
                }
                predicates.add(criteriaBuilder.or(modelTypePredicates.toArray(new Predicate[0])));
            }

            // 모델 이름 필터링
            if (modelNames != null && !modelNames.isEmpty()) {
                List<Predicate> modelNamePredicates = new ArrayList<>();
                for (String modelName : modelNames) {
                    modelNamePredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("modelName")), "%" + modelName.toLowerCase() + "%"));
                }
                predicates.add(criteriaBuilder.or(modelNamePredicates.toArray(new Predicate[0])));
            }

            // 주행거리 필터링
            if (mileageMin != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("mileage"), mileageMin));
            }
            if (mileageMax != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("mileage"), mileageMax));
            }

            // 연식 필터링
            if (yearMin != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("years"), yearMin));
            }
            if (yearMax != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("years"), yearMax));
            }

            // 연료 타입 필터링
            if (fuelTypes != null && !fuelTypes.isEmpty()) {
                List<Predicate> fuelTypePredicates = new ArrayList<>();
                for (String fuelType : fuelTypes) {
                    fuelTypePredicates.add(criteriaBuilder.like(root.get("fuelType"), "%" + fuelType + "%"));
                }
                predicates.add(criteriaBuilder.or(fuelTypePredicates.toArray(new Predicate[0])));
            }

            // 가격 필터링
            if (priceMin != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), priceMin));
            }
            if (priceMax != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), priceMax));
            }

            // 옵션 필터링
            Join<Object, Object> options = root.join("option");
            if (hasNavigation != null) {
                predicates.add(criteriaBuilder.equal(options.get("hasNavigation"), hasNavigation));
            }
            if (hasHiPass != null) {
                predicates.add(criteriaBuilder.equal(options.get("hasHiPass"), hasHiPass));
            }
            if (hasHeatedSteeringWheel != null) {
                predicates.add(criteriaBuilder.equal(options.get("hasHeatedSteeringWheel"), hasHeatedSteeringWheel));
            }
            if (hasHeatedSeats != null) {
                predicates.add(criteriaBuilder.equal(options.get("hasHeatedSeats"), hasHeatedSeats));
            }
            if (hasVentilatedSeats != null) {
                predicates.add(criteriaBuilder.equal(options.get("hasVentilatedSeats"), hasVentilatedSeats));
            }
            if (hasPowerSeats != null) {
                predicates.add(criteriaBuilder.equal(options.get("hasPowerSeats"), hasPowerSeats));
            }
            if (hasLeatherSeats != null) {
                predicates.add(criteriaBuilder.equal(options.get("hasLeatherSeats"), hasLeatherSeats));
            }
            if (hasPowerTrunk != null) {
                predicates.add(criteriaBuilder.equal(options.get("hasPowerTrunk"), hasPowerTrunk));
            }
            if (hasSunroof != null) {
                predicates.add(criteriaBuilder.equal(options.get("hasSunroof"), hasSunroof));
            }
            if (hasHUD != null) {
                predicates.add(criteriaBuilder.equal(options.get("hasHUD"), hasHUD));
            }
            if (hasSurroundViewMonitor != null) {
                predicates.add(criteriaBuilder.equal(options.get("hasSurroundViewMonitor"), hasSurroundViewMonitor));
            }
            if (hasRearMonitor != null) {
                predicates.add(criteriaBuilder.equal(options.get("hasRearMonitor"), hasRearMonitor));
            }
            if (hasBlindSpotWarning != null) {
                predicates.add(criteriaBuilder.equal(options.get("hasBlindSpotWarning"), hasBlindSpotWarning));
            }
            if (hasLaneDepartureWarning != null) {
                predicates.add(criteriaBuilder.equal(options.get("hasLaneDepartureWarning"), hasLaneDepartureWarning));
            }
            if (hasSmartCruiseControl != null) {
                predicates.add(criteriaBuilder.equal(options.get("hasSmartCruiseControl"), hasSmartCruiseControl));
            }
            if (hasFrontParkingWarning != null) {
                predicates.add(criteriaBuilder.equal(options.get("hasFrontParkingWarning"), hasFrontParkingWarning));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
