package com.example.whatcha.domain.order.domain;

import com.example.whatcha.domain.coupon.domain.UserCoupons;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_process")
@Entity
@Builder
public class OrderProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProcessId;

    @OneToOne
    @JoinColumn(name = "order_id")  // 외래 키 설정
    private Order order;

    @OneToOne
    @JoinColumn(name = "user_coupon_id")  // 외래 키 설정
    private UserCoupons userCoupons;

    private Boolean depositPaid;

    private Boolean contractSigned;

    private Boolean insuranceRegistered;

    private Boolean ownershipTransferred;

    private Boolean deliveryService;

    private Boolean fullyPaid;

    private Boolean deliveryCompleted;
}
