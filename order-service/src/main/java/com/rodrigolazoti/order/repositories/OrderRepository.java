package com.rodrigolazoti.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigolazoti.order.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
