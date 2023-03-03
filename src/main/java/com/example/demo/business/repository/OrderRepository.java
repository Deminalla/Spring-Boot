package com.example.demo.business.repository;

import com.example.demo.business.enums.OrderStatus;
import com.example.demo.business.repository.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByStatusEquals(OrderStatus status);
}
