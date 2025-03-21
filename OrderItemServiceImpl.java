package com.vivek.Vivek.Ecommerce.project.Service.Impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.vivek.Vivek.Ecommerce.project.Dto.OrderItemDto;
import com.vivek.Vivek.Ecommerce.project.Dto.OrderRequest;
import com.vivek.Vivek.Ecommerce.project.Dto.Response;
import com.vivek.Vivek.Ecommerce.project.Entities.Order;
import com.vivek.Vivek.Ecommerce.project.Entities.OrderItem;
import com.vivek.Vivek.Ecommerce.project.Entities.Product;
import com.vivek.Vivek.Ecommerce.project.Entities.User;
import com.vivek.Vivek.Ecommerce.project.Enums.OrderStatus;
import com.vivek.Vivek.Ecommerce.project.Exception.NotFoundException;
import com.vivek.Vivek.Ecommerce.project.Mapper.EntityDtoMapper;
import com.vivek.Vivek.Ecommerce.project.Repository.OrderItemRepository;
import com.vivek.Vivek.Ecommerce.project.Repository.OrderRepository;
import com.vivek.Vivek.Ecommerce.project.Repository.ProductRepository;
import com.vivek.Vivek.Ecommerce.project.Service.Interf.OrderItemService;
import com.vivek.Vivek.Ecommerce.project.Service.Interf.UserService;
import com.vivek.Vivek.Ecommerce.project.Specifcation.OrderItemSpecification;

import jakarta.validation.constraints.AssertFalse.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderItemServiceImpl implements OrderItemService{
	
	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	private final ProductRepository productRepository;
	private final UserService userService;
	private final EntityDtoMapper entityDtoMapper;
	
	public Response placeOrder(OrderRequest orderRequest) {
	    // Ensure that order items are not null or empty
	    if (orderRequest.getItems() == null || orderRequest.getItems().isEmpty()) {
	        return Response.builder()
	                .status(400)
	                .message("Order items cannot be null or empty")
	                .build();
	    }

	    User user = userService.getLoginUser();
	    
	    // Map the order items as before
	    java.util.List<OrderItem> orderItems = orderRequest.getItems().stream().map(orderItemRequest -> {
	        
	        Product product = productRepository.findById(orderItemRequest.getProductId())
	            .orElseThrow(() -> new NotFoundException("Product not found"));
	        
	        OrderItem orderItem = new OrderItem();
	        orderItem.setProduct(product);
	        orderItem.setCapacity(orderItemRequest.getQuantity());
	        orderItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(orderItemRequest.getQuantity()))); // set price according to the quantity
	        orderItem.setStatus(OrderStatus.PENDING);
	        orderItem.setUser(user);
	        
	        return orderItem;
	    }).collect(Collectors.toList());
	    
	    // Calculate total price
	    BigDecimal totalPrice = orderRequest.getTotalPrice() != null && orderRequest.getTotalPrice().compareTo(BigDecimal.ZERO) > 0
	            ? orderRequest.getTotalPrice()
	            : orderItems.stream().map(OrderItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

	    // Create order entity
	    Order order = new Order();
	    order.setOrderItemList(orderItems);
	    order.setTotalPrice(totalPrice);
	    
	    // Set the order reference in each orderItem
	    orderItems.forEach(orderItem -> orderItem.setOrder(order));
	    
	    orderRepository.save(order);

	    return Response.builder()
	            .status(200)
	            .message("Order was successfully placed")
	            .build();
	}



	public Response updateOrderItemStatus(Long orderItem, String status) {
		
		OrderItem orderItems = orderItemRepository.findById(orderItem)
		.orElseThrow(()-> new NotFoundException("Order Item not found"));
		
		orderItems.setStatus(OrderStatus.valueOf(status.toUpperCase()));
		
		orderItemRepository.save(orderItems);
		
		return Response.builder()
				.status(200)
				.message("order status updates successfiully")
				.build();
	}

	
	public Response FilterOrderItem(OrderStatus status, LocalDateTime startDate, LocalDateTime endDate, Long itemId,
			Pageable pageable) {
	
		Specification<OrderItem> spec=Specification.where(OrderItemSpecification.hasStatus(status))
				.and(OrderItemSpecification.createdBetween(startDate, endDate))
				.and(OrderItemSpecification.hasItemId(itemId));
		
		Page<OrderItem> orderItemPage=orderItemRepository.findAll(spec,pageable);
		
		if(orderItemPage.isEmpty())
		{
			throw new NotFoundException("No Order Found");
		}
		
		java.util.List<OrderItemDto> orderItemDtos=orderItemPage.getContent().stream()
				.map(entityDtoMapper::mapOrderItemToDtoplusProoductAndUser)
				.collect(Collectors.toList());
		
		return Response.builder()
				.status(200)
				.orderItemList(orderItemDtos)
				.totalPage(orderItemPage.getTotalPages())
				.totalElement((int) orderItemPage.getTotalElements())
				.build();
	}

}
