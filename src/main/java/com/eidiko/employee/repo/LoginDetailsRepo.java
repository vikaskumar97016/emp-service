package com.eidiko.employee.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eidiko.employee.entity.LoginDetails;

public interface LoginDetailsRepo extends JpaRepository<LoginDetails, Long>{

	//public Employee findByEmployee(Employee employee);
	
	
}
