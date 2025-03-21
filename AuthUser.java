package com.vivek.Vivek.Ecommerce.project.Security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.vivek.Vivek.Ecommerce.project.Entities.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthUser implements UserDetails{

	private User user;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return List.of(new SimpleGrantedAuthority(user.getRole().name()));
	}

	@Override
	public String getPassword() {
		
		return user.getPassword();
	}

	@Override
	public String getUsername() {
	
		return user.getEmail();
	}

}
