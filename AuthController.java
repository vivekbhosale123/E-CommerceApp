package com.vivek.Vivek.Ecommerce.project.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vivek.Vivek.Ecommerce.project.Dto.Response;
import com.vivek.Vivek.Ecommerce.project.Dto.UserDto;
import com.vivek.Vivek.Ecommerce.project.Dto.loginRequest;
import com.vivek.Vivek.Ecommerce.project.Service.Interf.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<Response> registerUser(@RequestBody UserDto registerRequest)
	{
		return ResponseEntity.ok(userService.registerUser(registerRequest));
	}
	
	@PostMapping("/login")
	public ResponseEntity<Response> loginUser(@RequestBody loginRequest loginRequest)
	{
		return ResponseEntity.ok(userService.loginUser(loginRequest));
	}
}
