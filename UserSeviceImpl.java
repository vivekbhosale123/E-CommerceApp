package com.vivek.Vivek.Ecommerce.project.Service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vivek.Vivek.Ecommerce.project.Dto.Response;
import com.vivek.Vivek.Ecommerce.project.Dto.UserDto;
import com.vivek.Vivek.Ecommerce.project.Dto.loginRequest;
import com.vivek.Vivek.Ecommerce.project.Entities.User;
import com.vivek.Vivek.Ecommerce.project.Enums.UserRole;
import com.vivek.Vivek.Ecommerce.project.Exception.InvalidCreaditialsException;
import com.vivek.Vivek.Ecommerce.project.Exception.NotFoundException;
import com.vivek.Vivek.Ecommerce.project.Mapper.EntityDtoMapper;
import com.vivek.Vivek.Ecommerce.project.Repository.UserRepository;
import com.vivek.Vivek.Ecommerce.project.Security.JwtUtils;
import com.vivek.Vivek.Ecommerce.project.Service.Interf.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserSeviceImpl implements UserService{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtUtils;
	private final EntityDtoMapper entityDtoMapper;
	
	@Override
	public Response registerUser(UserDto registrationRequest) {
		
		UserRole role = UserRole.USER;
		
		if(registrationRequest.getRole() !=null && registrationRequest.getRole().equalsIgnoreCase("admin"))
		{
		     role= UserRole.ADMIN;
		}
		User user=User.builder()
				.name(registrationRequest.getName())
				.email(registrationRequest.getEmail())
				.password(passwordEncoder.encode(registrationRequest.getPassword()))
				.phoneNumber(registrationRequest.getPhoneNumber())
				.role(role)
				.build();
		
		    User savedUser = userRepository.save(user);
		    
		    UserDto userDto = entityDtoMapper.mapUserToBasic(savedUser);
		    
		 return Response.builder()
				 .status(200)
				 .message("user added successfully")
				 .user(userDto)
				 .build();

		}

	@Override
	public Response loginUser(loginRequest loginRequest) {
		
		User user = userRepository.findByEmail(loginRequest.getEmail())
		.orElseThrow(()->new NotFoundException("Email not Found"));
		
		if(!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword()))
		{
			throw new InvalidCreaditialsException("Password does not match");
		}
		
		String token = jwtUtils.generateToken(user);
		
		return Response.builder()
				.status(200)
				.message("user successfully login")
				.token(token)
				.expirationTime("6 month")
				.role(user.getRole().name())
				.build();
	}

	@Override
	public Response getAllUsers() {
		
		List<User> users = userRepository.findAll();
		
		List<UserDto> userDtos = users.stream().map(entityDtoMapper::mapUserToBasic)
		.collect(Collectors.toList());
			
		
		
		return Response.builder()
				.status(200)
				.userList(userDtos)
				.build();
	}

	@Override
	public User getLoginUser() {
		
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		log.info("User Email Is: "+email);
		
		return userRepository.findByEmail(email).orElseThrow(()->new NotFoundException("user not found"));
	}

	@Override
	public Response getUserInfoAndOrderHistory() {
		
		User user = getLoginUser();
		
		UserDto userDto = entityDtoMapper.mapUserToDtoPlusAddressAndOrderItemHistory(user);
		
		return Response.builder()
				.status(200)
				.user(userDto)
				.build();
	}

}
