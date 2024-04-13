package com.wences.employeeApp.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.wences.employeeApp.dto.UserRegistrationDto;
import com.wences.employeeApp.model.User;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
}
