package com.vivek.Vivek.Ecommerce.project.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class loginRequest {

	@NotBlank(message = "email is required")
	private String email;
	
	@NotBlank(message = "email is required")
	private String password;
}
