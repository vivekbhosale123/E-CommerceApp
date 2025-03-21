package com.vivek.Vivek.Ecommerce.project.Service.Interf;

import com.vivek.Vivek.Ecommerce.project.Dto.Response;
import com.vivek.Vivek.Ecommerce.project.Dto.UserDto;
import com.vivek.Vivek.Ecommerce.project.Dto.loginRequest;
import com.vivek.Vivek.Ecommerce.project.Entities.User;

public interface UserService {

	Response registerUser(UserDto registrationRequest);
	
	Response loginUser(loginRequest loginRequest);
	
	Response getAllUsers();
	
	User getLoginUser();
	
	Response getUserInfoAndOrderHistory();
}
