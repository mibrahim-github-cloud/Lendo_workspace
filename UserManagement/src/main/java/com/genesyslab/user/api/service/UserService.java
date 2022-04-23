package com.genesyslab.user.api.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.genesyslab.user.api.controller.UserConflictException;
import com.genesyslab.user.api.model.shared.UserDto;

public interface UserService extends UserDetailsService{

	UserDto createUser(UserDto userDto) throws UserConflictException;
	
	UserDto getUserByUserId(String userId);
	
	UserDto loadUserDetailsByEmail(String email);
	
	void updateLastLogin(String email);
	
	List<UserDto> getUsers();
	
	void deleteUsers();
	
	void deleteUser(String userId);
	
	UserDto updateUser(UserDto userDto);
	
	
	
}
