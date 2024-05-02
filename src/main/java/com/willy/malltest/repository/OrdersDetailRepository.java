package com.willy.malltest.repository;

import com.willy.malltest.model.OrdersDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersDetailRepository extends JpaRepository<OrdersDetail, Integer> {
}
