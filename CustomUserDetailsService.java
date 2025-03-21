package com.vivek.Vivek.Ecommerce.project.Security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vivek.Vivek.Ecommerce.project.Entities.User;
import com.vivek.Vivek.Ecommerce.project.Exception.NotFoundException;
import com.vivek.Vivek.Ecommerce.project.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user=userRepository.findByEmail(username).orElseThrow(()->new NotFoundException("User/Email not found"));
		
		return AuthUser.builder()
				.user(user)
				.build();
	}

}
