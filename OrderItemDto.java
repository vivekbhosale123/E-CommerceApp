package com.vivek.Vivek.Ecommerce.project.Dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vivek.Vivek.Ecommerce.project.Enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
	
	private Long id;
	
	private int capacity;
	
	private BigDecimal price;
	
	private OrderStatus status;
	
	private UserDto user;
	
	private ProductDto product;

	private OrderDto order;
	
	private LocalDateTime createdAt;
}
