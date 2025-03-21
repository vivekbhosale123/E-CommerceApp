package com.vivek.Vivek.Ecommerce.project.Specifcation;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.vivek.Vivek.Ecommerce.project.Entities.OrderItem;
import com.vivek.Vivek.Ecommerce.project.Enums.OrderStatus;

public class OrderItemSpecification {

	// specification to filter order by status
	
	public static Specification<OrderItem> hasStatus(OrderStatus status)
	{
		return ((root,query,criteriaBuilder)-> 
		status!=null ? criteriaBuilder.equal(root.get("status"),status):null);
	}
	
	
	// specification to filter order items by data range
	
	public static Specification<OrderItem> createdBetween(LocalDateTime startDate,LocalDateTime endDate)
	{
		return ((root,query,criteriaBuilder)-> 
		{
			if(startDate!=null && endDate!=null)
			{
				return criteriaBuilder.between(root.get("createdAt"),startDate, endDate);
			}
			else if(startDate!=null)
			{
			   return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDate);
			}
			else if(endDate!=null)
			{
				return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"),endDate);
			}
			else
			{
				return null;
			}
		});
	}
	
	// generate specification to filter orderItems by item id
	
	public static Specification<OrderItem> hasItemId(Long itemId)
	{
		return ((root,query,criteriaBuilder)-> 
		itemId!=null ? criteriaBuilder.equal(root.get("id"),itemId):null);
	}
}
