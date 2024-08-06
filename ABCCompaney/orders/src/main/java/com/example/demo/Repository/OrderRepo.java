package com.example.demo.Repository;

import com.example.demo.Model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepo extends JpaRepository<Orders, Integer> {
    @Query(value = "SELECT * FROM order WHERE id = ?1", nativeQuery = true)
    Orders getOrderById(Integer orderId);
}
