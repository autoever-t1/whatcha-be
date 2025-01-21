package com.example.whatcha.domain.order.dao;

import com.example.whatcha.domain.order.domain.Order;
import com.example.whatcha.domain.order.dto.response.OrderResDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderId(Long orderId);

    @Query("SELECT SUM(o.fullPayment) FROM Order o")
    Long getTotalSales();

    List<Order> findAllByOrderByCreatedAtDesc();

    List<Order> findByUserId(Long userId);
}
