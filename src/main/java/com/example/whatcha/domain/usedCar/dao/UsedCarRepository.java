package com.example.whatcha.domain.usedCar.dao;

import com.example.whatcha.domain.usedCar.domain.UsedCar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsedCarRepository extends JpaRepository<UsedCar, Long>, JpaSpecificationExecutor<UsedCar> {

    @Query("SELECT u FROM UsedCar u WHERE " +
            "(u.price BETWEEN :minPrice AND :maxPrice) AND " +
            "(u.modelName LIKE %:modelName1% OR u.modelName LIKE %:modelName2% OR u.modelName LIKE %:modelName3%)")
    List<UsedCar> findRecommendedCars(
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("modelName1") String modelName1,
            @Param("modelName2") String modelName2,
            @Param("modelName3") String modelName3,
            Pageable pageable
    );


    @Query("""
    SELECT uc FROM UsedCar uc
    WHERE uc.usedCarId NOT IN :excludeIds
    ORDER BY uc.createdAt DESC""")
    List<UsedCar> findByIdNotIn(
            @Param("excludeIds") List<Long> excludeIds,
            Pageable pageable);

    List<UsedCar> findByBranchStore_BranchStoreId(Long branchStoreId);

    Page<UsedCar> findByModelNameContainingIgnoreCaseOrVhclRegNoContainingIgnoreCase(String modelName, String vhclRegNo, Pageable pageable);

}
