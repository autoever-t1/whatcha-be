package com.example.whatcha.domain.coupon.domain;

import com.example.whatcha.domain.order.domain.Order;
import com.example.whatcha.domain.order.domain.OrderProcess;
import com.example.whatcha.domain.user.domain.User;
import com.example.whatcha.global.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_coupons")
@Entity
@Builder
public class UserCoupons extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCouponId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

//    @OneToOne(mappedBy = "userCoupons")
//    private OrderProcess orderProcess;

    private LocalDate expiryDate;

    private Boolean isActive;
}
