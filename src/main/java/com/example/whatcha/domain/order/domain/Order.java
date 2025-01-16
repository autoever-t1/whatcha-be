package com.example.whatcha.domain.order.domain;

import com.example.whatcha.domain.coupon.domain.UserCoupons;
import com.example.whatcha.global.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@Entity
@Builder
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    //usercoupon이랑 단방향 매핑
    @OneToOne
    @JoinColumn(name = "user_coupon_id", unique = true)  // 외래 키 설정
    private UserCoupons userCoupons;

    private Long usedCarId;

    private Long userId;

    private int fullPayment; //총 주문 금액

    private int deposit; //계약금

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private OrderProcess orderProcess;
}
