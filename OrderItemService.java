package com.vivek.Vivek.Ecommerce.project.Service.Interf;

import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;

import com.vivek.Vivek.Ecommerce.project.Dto.OrderRequest;
import com.vivek.Vivek.Ecommerce.project.Dto.Response;
import com.vivek.Vivek.Ecommerce.project.Enums.OrderStatus;

public interface OrderItemService {

	Response placeOrder(OrderRequest orderRequest);
	
	Response updateOrderItemStatus(Long orderItem,String status);
	
	Response FilterOrderItem(OrderStatus status,LocalDateTime startDate,LocalDateTime endDate,Long itemId,Pageable pageable);
}
