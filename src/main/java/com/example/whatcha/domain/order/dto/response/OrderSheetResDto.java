package com.example.whatcha.domain.order.dto.response;

import com.example.whatcha.domain.admin.dto.response.BranchStoreResDto;
import com.example.whatcha.domain.branchStore.domain.BranchStore;
import com.example.whatcha.domain.coupon.dto.response.CouponResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderSheetResDto {
    private Integer price;

    private String registrationDate;

    private String vhclRegNo;

    private String modelName;

    private String mainImage;

    private String mileage;

    private CouponResDto couponInfo;

    private OrderProcessResDto orderProcessInfo;

    private OrderResDto orderInfo;

    private BranchStoreResDto branchStoreInfo;
}
