package com.vivek.Vivek.Ecommerce.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivek.Vivek.Ecommerce.project.Entities.Order;

public interface OrderRepository extends JpaRepository<Order,Long>{

}
