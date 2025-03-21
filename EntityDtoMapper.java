package com.vivek.Vivek.Ecommerce.project.Mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.vivek.Vivek.Ecommerce.project.Dto.AddressDto;
import com.vivek.Vivek.Ecommerce.project.Dto.CategoryDto;
import com.vivek.Vivek.Ecommerce.project.Dto.OrderItemDto;
import com.vivek.Vivek.Ecommerce.project.Dto.ProductDto;
import com.vivek.Vivek.Ecommerce.project.Dto.UserDto;
import com.vivek.Vivek.Ecommerce.project.Entities.Address;
import com.vivek.Vivek.Ecommerce.project.Entities.Category;
import com.vivek.Vivek.Ecommerce.project.Entities.OrderItem;
import com.vivek.Vivek.Ecommerce.project.Entities.Product;
import com.vivek.Vivek.Ecommerce.project.Entities.User;

@Component
public class EntityDtoMapper {

	// user entity to User Dto
	public UserDto mapUserToBasic(User user)
	{
		UserDto userDto=new UserDto();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setRole(user.getRole().name());
		
		return userDto;
	}
	
	// Address entity to Address Dto
	public AddressDto mapAddressToBasic(Address address)
	{
		AddressDto addressDto=new AddressDto();
		addressDto.setId(address.getId());
		addressDto.setStreet(address.getStreet());
		addressDto.setCity(address.getCity());
		addressDto.setCountry(address.getCountry());
		addressDto.setState(address.getState());
		addressDto.setZipCode(address.getZipCode());
		
		return addressDto;
	}
	
	// Category entity to CategoryDto
	public CategoryDto mapCategoryToBasic(Category category)
	{
		CategoryDto categoryDto=new CategoryDto();
		categoryDto.setId(category.getId());
		categoryDto.setName(category.getName());
		
		return categoryDto;
	}
	
	// OrderItem entity to OrderItemDto
	public OrderItemDto mapOrderItemToBasic(OrderItem orderItem)
	{
		OrderItemDto orderItemDto=new OrderItemDto();
		orderItemDto.setId(orderItem.getId());
		orderItemDto.setCapacity(orderItem.getCapacity());
		orderItemDto.setPrice(orderItem.getPrice());
		orderItemDto.setStatus(orderItem.getStatus());
		orderItemDto.setCreatedAt(orderItem.getCreatedAt());
		
		return orderItemDto;
	}
	
	//Product entity to productDto
	public ProductDto mapProductToBasic(Product product)
	{
		ProductDto productDto=new ProductDto();
		productDto.setId(product.getId());
		productDto.setName(product.getName());
		productDto.setDescription(product.getDescription());
		productDto.setImageUrl(product.getImageUrl());
		productDto.setPrice(product.getPrice());
		
		return productDto;
	}
	
	// userDto to DTO plus Address
	
	public UserDto mapUserToDtoPlusAddress(User user)
	{
		UserDto userDto=mapUserToBasic(user);
		
		if(user.getAddress()!=null)
		{
			AddressDto addressDto=mapAddressToBasic(user.getAddress());
			userDto.setAddress(addressDto);
		}
		
		return userDto;
	}
	
	// orderItem to DTO plus Product
	public OrderItemDto mapOrderItemToDtoplusProoduct(OrderItem orderItem)
	{
		OrderItemDto orderItemDto=mapOrderItemToBasic(orderItem);
		
		if(orderItem.getProduct()!=null)
		{
			ProductDto productDto=mapProductToBasic(orderItem.getProduct());
			orderItemDto.setProduct(productDto);
		}
		
		return orderItemDto;
	}
	
	// OrderItem to Dto plus Product and User
	public OrderItemDto mapOrderItemToDtoplusProoductAndUser(OrderItem orderItem)
	{
		OrderItemDto orderItemDto=mapOrderItemToDtoplusProoduct(orderItem);
		
		if(orderItem.getUser()!=null)
		{
			UserDto userDto=mapUserToDtoPlusAddress(orderItem.getUser());
			orderItemDto.setUser(userDto);
		}
		
		return orderItemDto;
	}
	
	// Order to DTO with address and Order Items History
	
	public UserDto mapUserToDtoPlusAddressAndOrderItemHistory(User user)
	{
		UserDto userDto=mapUserToDtoPlusAddress(user);
		
		if(user.getOrderItemList()!=null && !user.getOrderItemList().isEmpty())
		{
			userDto.setOrderItemList(user.getOrderItemList()
					.stream()
					.map(this::mapOrderItemToDtoplusProoduct)
					.collect(Collectors.toList()));
		}
		
		return userDto;
	}
	
	
	
	
	
}
