package com.vivek.Vivek.Ecommerce.project.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vivek.Vivek.Ecommerce.project.Dto.Response;
import com.vivek.Vivek.Ecommerce.project.Service.Interf.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	
	@GetMapping("/get-all")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> getAllusers()
	{
		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	@GetMapping("/my-info")
	public ResponseEntity<Response> getMyInfoAndOrderHostroy()
	{
		return ResponseEntity.ok(userService.getUserInfoAndOrderHistory());
	}
}
