package com.eidiko.employee.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eidiko.employee.entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

	public Page<Employee> findAllByIsDeleted(boolean isDeleted, Pageable pageable);
	
	
}
