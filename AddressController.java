package com.vivek.Vivek.Ecommerce.project.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vivek.Vivek.Ecommerce.project.Dto.AddressDto;
import com.vivek.Vivek.Ecommerce.project.Dto.Response;
import com.vivek.Vivek.Ecommerce.project.Service.Interf.AddressService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

	private final AddressService addressService;
	
	@PostMapping("/save")
	public ResponseEntity<Response> saveAndUpdateAddress(@RequestBody AddressDto addressDto)
	{
		return ResponseEntity.ok(addressService.saveAndUpdateAddress(addressDto));
	}
}
