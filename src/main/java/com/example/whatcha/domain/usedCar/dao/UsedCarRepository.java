package com.example.whatcha.domain.usedCar.dao;

import com.example.whatcha.domain.usedCar.domain.UsedCar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsedCarRepository extends JpaRepository<UsedCar, Long> {

    @Query("SELECT u FROM UsedCar u WHERE u.modelName LIKE %:keyword% OR u.vhclRegNo LIKE %:keyword%")
    Page<UsedCar> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT u FROM UsedCar u " +
            "LEFT JOIN u.option o " +
            "WHERE (:colorIds IS NULL OR u.color.colorId IN :colorIds) " +
            "AND (:modelTypes IS NULL OR u.modelType IN :modelTypes) " +
            "AND (:modelNames IS NULL OR u.modelName IN :modelNames) " +
            "AND (:mileageMin IS NULL OR u.mileage >= :mileageMin) " +
            "AND (:mileageMax IS NULL OR u.mileage <= :mileageMax) " +
            "AND (:yearMin IS NULL OR u.years >= :yearMin) " +
            "AND (:yearMax IS NULL OR u.years <= :yearMax) " +
            "AND (:fuelTypes IS NULL OR u.fuelType IN :fuelTypes) " +
            "AND (:hasNavigation IS NULL OR o.hasNavigation = :hasNavigation) " +
            "AND (:hasHiPass IS NULL OR o.hasHiPass = :hasHiPass) " +
            "AND (:hasHeatedSteeringWheel IS NULL OR o.hasHeatedSteeringWheel = :hasHeatedSteeringWheel) " +
            "AND (:hasHeatedSeats IS NULL OR o.hasHeatedSeats = :hasHeatedSeats) " +
            "AND (:hasVentilatedSeats IS NULL OR o.hasVentilatedSeats = :hasVentilatedSeats) " +
            "AND (:hasPowerSeats IS NULL OR o.hasPowerSeats = :hasPowerSeats) " +
            "AND (:hasLeatherSeats IS NULL OR o.hasLeatherSeats = :hasLeatherSeats) " +
            "AND (:hasPowerTrunk IS NULL OR o.hasPowerTrunk = :hasPowerTrunk) " +
            "AND (:hasSunroof IS NULL OR o.hasSunroof = :hasSunroof) " +
            "AND (:hasHUD IS NULL OR o.hasHUD = :hasHUD) " +
            "AND (:hasSurroundViewMonitor IS NULL OR o.hasSurroundViewMonitor = :hasSurroundViewMonitor) " +
            "AND (:hasRearMonitor IS NULL OR o.hasRearMonitor = :hasRearMonitor) " +
            "AND (:hasBlindSpotWarning IS NULL OR o.hasBlindSpotWarning = :hasBlindSpotWarning) " +
            "AND (:hasLaneDepartureWarning IS NULL OR o.hasLaneDepartureWarning = :hasLaneDepartureWarning) " +
            "AND (:hasSmartCruiseControl IS NULL OR o.hasSmartCruiseControl = :hasSmartCruiseControl) " +
            "AND (:hasFrontParkingWarning IS NULL OR o.hasFrontParkingWarning = :hasFrontParkingWarning)")
    Page<UsedCar> filterUsedCars(@Param("colorIds") List<Long> colorIds,
                                 @Param("modelTypes") List<String> modelTypes,
                                 @Param("modelNames") List<String> modelNames,
                                 @Param("mileageMin") Integer mileageMin,
                                 @Param("mileageMax") Integer mileageMax,
                                 @Param("yearMin") Integer yearMin,
                                 @Param("yearMax") Integer yearMax,
                                 @Param("fuelTypes") List<String> fuelTypes,
                                 @Param("hasNavigation") Boolean hasNavigation,
                                 @Param("hasHiPass") Boolean hasHiPass,
                                 @Param("hasHeatedSteeringWheel") Boolean hasHeatedSteeringWheel,
                                 @Param("hasHeatedSeats") Boolean hasHeatedSeats,
                                 @Param("hasVentilatedSeats") Boolean hasVentilatedSeats,
                                 @Param("hasPowerSeats") Boolean hasPowerSeats,
                                 @Param("hasLeatherSeats") Boolean hasLeatherSeats,
                                 @Param("hasPowerTrunk") Boolean hasPowerTrunk,
                                 @Param("hasSunroof") Boolean hasSunroof,
                                 @Param("hasHUD") Boolean hasHUD,
                                 @Param("hasSurroundViewMonitor") Boolean hasSurroundViewMonitor,
                                 @Param("hasRearMonitor") Boolean hasRearMonitor,
                                 @Param("hasBlindSpotWarning") Boolean hasBlindSpotWarning,
                                 @Param("hasLaneDepartureWarning") Boolean hasLaneDepartureWarning,
                                 @Param("hasSmartCruiseControl") Boolean hasSmartCruiseControl,
                                 @Param("hasFrontParkingWarning") Boolean hasFrontParkingWarning,
                                 Pageable pageable);
}
