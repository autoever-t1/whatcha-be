package com.example.whatcha.domain.order.service;

import com.example.whatcha.domain.branchStore.dao.BranchStoreRepository;
import com.example.whatcha.domain.branchStore.domain.BranchStore;
import com.example.whatcha.domain.coupon.dao.CouponRepository;
import com.example.whatcha.domain.coupon.dao.UserCouponsRepository;
import com.example.whatcha.domain.coupon.domain.UserCoupons;
import com.example.whatcha.domain.order.dao.OrderProcessRepository;
import com.example.whatcha.domain.order.dao.OrderRepository;
import com.example.whatcha.domain.order.domain.Order;
import com.example.whatcha.domain.order.domain.OrderProcess;
import com.example.whatcha.domain.order.dto.response.DepositResDto;
import com.example.whatcha.domain.order.dto.response.OrderProcessResDto;
import com.example.whatcha.domain.order.dto.response.OrderResDto;
import com.example.whatcha.domain.usedCar.dao.ModelRepository;
import com.example.whatcha.domain.usedCar.dao.UsedCarRepository;
import com.example.whatcha.domain.usedCar.domain.Model;
import com.example.whatcha.domain.usedCar.domain.UsedCar;
import com.example.whatcha.domain.user.dao.UserRepository;
import com.example.whatcha.domain.user.domain.User;
import com.example.whatcha.global.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProcessRepository orderProcessRepository;
    private final UserCouponsRepository userCouponsRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final UsedCarRepository usedCarRepository;
    private final BranchStoreRepository branchStoreRepository;
    private final ModelRepository modelRepository;

    @Override
    public OrderProcessResDto getOrderProcess(Long orderId) {
        //orderId를 통해 OrderProcess를 조회
        OrderProcess orderProcess = orderProcessRepository.findByOrder_OrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("OrderProcess not found for orderId: " + orderId));

        //OrderProcess를 OrderProcessResDto로 변환
        OrderProcessResDto orderProcessResDto = OrderProcessResDto.toDto(orderProcess);
        return orderProcessResDto;
    }

    @Override
    public OrderResDto getOrder(Long orderId) {
        //orderId를 통해 Order를 조회
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found for orderId: " + orderId));

        //Order를 OrderResDto로 변환
        OrderResDto orderResDto = OrderResDto.toDto(order);
        return orderResDto;
    }

    //이때 order, orderprocess save해야함
    @Override
    @Transactional
    public DepositResDto payDeposit(String email, Long usedCarId, int fullPayment, int deposit, Long userCouponId) {

        //해야할 일 -> 쿠폰아이디 받으면 쿠폰아이디로 쿠폰 객체 찾고 계산해서 가격 반환하기
        //userCoupons 찾기
        UserCoupons userCoupons = userCouponsRepository.findById(userCouponId).orElseThrow(() -> new IllegalArgumentException("Invalid userCouponId: " + userCouponId));

        //usedCar 찾기
        UsedCar usedCar = usedCarRepository.findById(usedCarId).orElseThrow(() -> new IllegalArgumentException("Invalid usedCarId: " + usedCarId));

        //usedCar status값 바꿔주기
        UsedCar updatedUsedCar = usedCar.changeStatus("판매중");
        usedCarRepository.save(updatedUsedCar);

        //해당 branch ownedCarCount -1 해주기
        Long branchStoreId = usedCar.getBranchStore().getBranchStoreId();
        BranchStore branchStore = branchStoreRepository.findById(branchStoreId).orElseThrow(() -> new IllegalArgumentException("Invalid branchStoreId: " + branchStoreId));

        BranchStore updatedBranchStore = BranchStore.builder()
                .branchStoreId(branchStore.getBranchStoreId())
                .branchStoreName(branchStore.getBranchStoreName())
                .location(branchStore.getLocation())
                .latitude(branchStore.getLatitude())
                .longitude(branchStore.getLongitude())
                .ownedCarCount(branchStore.getOwnedCarCount() - 1)
                .phone(branchStore.getPhone())
                .build();

        branchStoreRepository.save(updatedBranchStore);

        //model 찾아서 판매 갯수 +1 해주기
        Long modelId = usedCar.getModel().getModelId();
        Model model = modelRepository.findById(modelId).orElseThrow(() -> new IllegalArgumentException("Invalid modelId: " + modelId));

        Model updatedModel = Model.builder()
                .modelId(model.getModelId())
                .modelName(model.getModelName())
                .modelType(model.getModelType())
                .factoryPrice(model.getFactoryPrice())
                .orderCount(model.getOrderCount()+1)
                .build();

        modelRepository.save(updatedModel);

        //user 찾기
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Invalid userEmail: " + email));

        //Order 저장
        Order order = Order.builder()
                .userCoupons(userCoupons)
                .usedCarId(usedCarId)
                .userId(user.getUserId())
                .fullPayment(fullPayment)
                .deposit(deposit)
                .build();

        orderRepository.save(order);

        //orderprocess에서 depositPaid true로 바꾸고 나머지 다 false
        OrderProcess orderProcess = OrderProcess.builder()
                .order(order)
                .userCoupons(userCoupons)
                .depositPaid(true)
                .contractSigned(false)
                .deliveryService(false)
                .fullyPaid(false)
                .deliveryCompleted(false)
                .build();

        orderProcessRepository.save(orderProcess);

        //usercoupon에서 isActive false로 바꾸고 remaining amount계산해서 넘기기
        userCouponsRepository.deleteByCouponCouponId(userCouponId);

        DepositResDto depositResDto = DepositResDto.builder()
                .orderId(order.getOrderId())
                .nickName(user.getName())
                .vhclRegNo(usedCar.getVhclRegNo())
                .registrationDate(order.getCreatedAt())
                .modelName(usedCar.getModelName())
                .deposit(deposit)
                .remainingAmount(fullPayment-deposit)
                .build();
        return depositResDto;
    }

    @Override
    public void fullPayment(Long orderId) {
        //orderId를 통해 OrderProcess를 조회
        OrderProcess orderProcess = orderProcessRepository.findByOrder_OrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("OrderProcess not found for orderId: " + orderId));

        OrderProcessResDto orderProcessResDto = OrderProcessResDto.toDto(orderProcess);
        orderProcessResDto.setFullyPaid(true);

        OrderProcess result = OrderProcess.builder()
                .order(orderProcess.getOrder())
                .userCoupons(orderProcess.getUserCoupons())
                .depositPaid(orderProcessResDto.getDepositPaid())
                .contractSigned(orderProcessResDto.getContractSigned())
                .fullyPaid(orderProcessResDto.getFullyPaid())
                .deliveryService(orderProcessResDto.getDeliveryService())
                .deliveryCompleted(orderProcessResDto.getDeliveryCompleted())
                .build();
        orderProcessRepository.save(result);
    }

    @Override
    public void writeContract(Long orderId) {
        //orderId를 통해 OrderProcess를 조회
        OrderProcess orderProcess = orderProcessRepository.findByOrder_OrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("OrderProcess not found for orderId: " + orderId));

        OrderProcessResDto orderProcessResDto = OrderProcessResDto.toDto(orderProcess);
        orderProcessResDto.setContractSigned(true);

        OrderProcess result = OrderProcess.builder()
                .order(orderProcess.getOrder())
                .userCoupons(orderProcess.getUserCoupons())
                .depositPaid(orderProcessResDto.getDepositPaid())
                .contractSigned(orderProcessResDto.getContractSigned())
                .fullyPaid(orderProcessResDto.getFullyPaid())
                .deliveryService(orderProcessResDto.getDeliveryService())
                .deliveryCompleted(orderProcessResDto.getDeliveryCompleted())
                .build();
        orderProcessRepository.save(result);
    }

    @Override
    public void deliveryService(Long orderId) {
        //orderId를 통해 OrderProcess를 조회
        OrderProcess orderProcess = orderProcessRepository.findByOrder_OrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("OrderProcess not found for orderId: " + orderId));

        OrderProcessResDto orderProcessResDto = OrderProcessResDto.toDto(orderProcess);
        orderProcessResDto.setDeliveryService(true);

        OrderProcess result = OrderProcess.builder()
                .order(orderProcess.getOrder())
                .userCoupons(orderProcess.getUserCoupons())
                .depositPaid(orderProcessResDto.getDepositPaid())
                .contractSigned(orderProcessResDto.getContractSigned())
                .fullyPaid(orderProcessResDto.getFullyPaid())
                .deliveryService(orderProcessResDto.getDeliveryService())
                .deliveryCompleted(orderProcessResDto.getDeliveryCompleted())
                .build();
        orderProcessRepository.save(result);
    }

}
