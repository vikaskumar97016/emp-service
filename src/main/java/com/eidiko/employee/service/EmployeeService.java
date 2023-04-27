package com.eidiko.employee.service;

import java.util.List;
import java.util.Map;

import javax.security.sasl.AuthenticationException;

import com.eidiko.employee.dto.EmpWorkLocationReqDto;
import com.eidiko.employee.dto.ManagerRequestDto;
import com.eidiko.employee.dto.ShiftTimingReqDto;
import org.springframework.data.domain.Pageable;

import com.eidiko.employee.dto.ChangePasswordDto;
import com.eidiko.employee.entity.Employee;

public interface EmployeeService {

	
	
	public Employee userByUserName(String userName);
	
	public Map<String, Object> forgotPassword(long empId);
	
	public Map<String,Object> createEmployee(Employee employee) throws AuthenticationException;
	
	public List<Employee> getAllEmployee(Pageable pageable);
	
	public Map<String, Object> changePassword (ChangePasswordDto changePasswordDto) throws AuthenticationException ;
	
	public Map<String, Object> getEmployeeById(long empId);
	
	public Map<String, Object> searchEmployeeById(long empId);


	public Map<String, Object> addShiftTiming(ShiftTimingReqDto shiftTimingReqDto) throws NumberFormatException,AuthenticationException;

	public Map<String, Object> addWorkLocation(EmpWorkLocationReqDto empWorkLocationReqDto) throws NumberFormatException,AuthenticationException;

	public Map<String, Object> addReportingManager(ManagerRequestDto managerRequestDto) throws AuthenticationException;

	public Map<String, Object> addAbout(String about) throws AuthenticationException;

}
