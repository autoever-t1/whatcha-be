package com.example.whatcha.domain.admin.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DashBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dashBoardId;

    private Long userCount;

    private Long orderCount;

    private Long totalSales;

    private Long carStock;

    @Column(unique = true)  // unique 제약조건 추가
    private LocalDate date;

    public void updateCounts(Long userCount, Long orderCount, Long totalSales, Long carStock) {
        this.userCount = userCount;
        this.orderCount = orderCount;
        this.totalSales = totalSales;
        this.carStock = carStock;
    }
}