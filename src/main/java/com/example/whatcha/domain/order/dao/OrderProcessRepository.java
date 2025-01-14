package com.example.whatcha.domain.order.dao;

import com.example.whatcha.domain.order.domain.OrderProcess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderProcessRepository extends JpaRepository<OrderProcess,Long> {

    Optional<OrderProcess> findByOrder_OrderId(Long orderId);
}
