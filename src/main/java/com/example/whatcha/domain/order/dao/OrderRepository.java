package com.example.whatcha.domain.order.dao;

import com.example.whatcha.domain.order.domain.Order;
import com.example.whatcha.domain.order.dto.response.OrderResDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderId(Long orderId);
}
