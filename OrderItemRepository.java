package com.vivek.Vivek.Ecommerce.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.vivek.Vivek.Ecommerce.project.Entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long>,JpaSpecificationExecutor<OrderItem>{

}
