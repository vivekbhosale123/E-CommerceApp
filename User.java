package com.vivek.Vivek.Ecommerce.project.Entities;

import java.time.LocalDateTime;
import java.util.List;

import com.vivek.Vivek.Ecommerce.project.Enums.UserRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@jakarta.persistence.Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	
	@NotBlank(message = "name is required")
	private String name;
	
	@Column(unique = true)
	@NotBlank(message = "email is required")
	private String email;
	
	@NotBlank(message = "password is required")
	private String password;
	
	@Column(name = "phone_number")
	@NotBlank(message = "phone number is required")
	private String phoneNumber;
	
	private UserRole role;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Address address;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<OrderItem> orderItemList;
	
	@Column(name = "created_At")
	private final LocalDateTime createdAt=LocalDateTime.now();
}
