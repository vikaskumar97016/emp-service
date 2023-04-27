package com.eidiko.employee.config;


import javax.security.sasl.AuthenticationException;

import com.eidiko.employee.helper.ConstantValues;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.eidiko.employee.entity.Employee;
import com.eidiko.employee.entity.EmployeeAccessLevel;

public class SecurityUtil {
	
	private SecurityUtil() {}

	public static Employee getCurrentUserDetails() throws AuthenticationException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.isAuthenticated()) {
			Object principal = authentication.getPrincipal();
			if(principal != null) {
				Employee employee = (Employee) principal;
				return employee;
			}
			
		}
		throw new AuthenticationException(ConstantValues.SESSION_HAS_BEEN_EXPIRED);
	}
	
	
	public static boolean isAuthenticatedUser() throws AuthenticationException {
		return getCurrentUserDetails() != null;
	}
	
	
	
}
