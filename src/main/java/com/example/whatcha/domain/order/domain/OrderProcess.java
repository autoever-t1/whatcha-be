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

    @Builder.Default
    private Boolean depositPaid = false;

    @Builder.Default
    private Boolean contractSigned = false;

    @Builder.Default
    private Boolean fullyPaid = false;

    @Builder.Default
    private Boolean deliveryService = false;

    @Builder.Default
    private Boolean deliveryCompleted = false;

    public void markAsFullyPaid() {
        this.fullyPaid = true;
    }

    public void signContract() {
        this.contractSigned = true;
    }

    public void enableDeliveryService() {
        this.deliveryService = true;
    }
}
