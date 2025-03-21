package com.vivek.Vivek.Ecommerce.project.Dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vivek.Vivek.Ecommerce.project.Entities.Payment;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderRequest {

	private BigDecimal totalPrice;
	
	private List<OrderItemRequest> items;
	
	private Payment paymentInfo;
}
