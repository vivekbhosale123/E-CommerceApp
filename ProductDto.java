package com.vivek.Vivek.Ecommerce.project.Dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
public class ProductDto {

	
	private Long id;
	
	private String description;
	
	private String imageUrl;
	
	private String name;
	
	private BigDecimal price;
		
	private CategoryDto category;

	private LocalDateTime createdAt;
}
