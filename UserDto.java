package com.vivek.Vivek.Ecommerce.project.Dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private Long Id;
	
	private String name;
	
	private String email;
	
	private String password;
	
	private String phoneNumber;
	
	private String role;
	
	private AddressDto address;
	
	private List<OrderItemDto> orderItemList;
}
