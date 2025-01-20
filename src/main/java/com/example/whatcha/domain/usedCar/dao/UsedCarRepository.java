package com.example.whatcha.domain.usedCar.dao;

import com.example.whatcha.domain.usedCar.domain.UsedCar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsedCarRepository extends JpaRepository<UsedCar, Long>, JpaSpecificationExecutor<UsedCar> {

    @Query(""" 
            SELECT uc FROM UsedCar uc WHERE 
            (uc.price BETWEEN :minPrice AND :maxPrice) AND 
            (uc.modelName LIKE %:model1% OR uc.modelName LIKE %:model2% OR uc.modelName LIKE %:model3%)
            AND uc.status = '구매 가능' """)
    List<UsedCar> findRecommendedCars(
            Integer minPrice,
            Integer maxPrice,
            String model1,
            String model2,
            String model3,
            Pageable pageable
    );


    @Query("""
    SELECT uc FROM UsedCar uc
    WHERE uc.usedCarId NOT IN :excludeIds AND uc.status = '구매 가능'
    ORDER BY uc.createdAt DESC""")
    List<UsedCar> findByIdNotIn(
            List<Long> excludeIds,
            Pageable pageable);

    List<UsedCar> findByStatusOrderByLikeCountDesc(String status, Pageable pageable);



    @Query("SELECT u FROM UsedCar u WHERE u.modelName LIKE %:keyword% OR u.vhclRegNo LIKE %:keyword%")
    Page<UsedCar> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    List<UsedCar> findByBranchStore_BranchStoreId(Long branchStoreId);

    Page<UsedCar> findByModelNameContainingIgnoreCaseOrVhclRegNoContainingIgnoreCase(String modelName, String vhclRegNo, Pageable pageable);

    Optional<UsedCar> findByGoodsNo(String goodsNo);
}
