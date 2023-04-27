package com.eidiko.employee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.sasl.AuthenticationException;

import com.eidiko.employee.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import com.eidiko.employee.config.SecurityUtil;
import com.eidiko.employee.entity.Employee;
import com.eidiko.employee.exception.ResourceNotProcessedException;
import com.eidiko.employee.exception.TokenNotValidException;
import com.eidiko.employee.helper.ConstantValues;
import com.eidiko.employee.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ModelMapper mapper;

	@PostMapping("/create")
	public ResponseEntity<Map<String, Object>> createEmployee(@Valid @RequestBody EmployeeDto employeeDto)
			throws AuthenticationException {
		try {
			SecurityUtil.isAuthenticatedUser();
		} catch (AuthenticationException e) {
			throw new TokenNotValidException(ConstantValues.SESSION_HAS_BEEN_EXPIRED);
		}
		Map<String, Object> map = this.employeeService.createEmployee(this.mapper.map(employeeDto, Employee.class));
		return ResponseEntity.ok().body(map);
	}

	@GetMapping("/get-all-employee")
	// @PreAuthorize("hasEmployeeAccessLevel('EMP_LEVEL_ACCESS')")
	public ResponseEntity<Map<String, Object>> getAllEmployee(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "5") Integer pageSize,
			@RequestParam(defaultValue = ConstantValues.EMP_ID) String sortBy) throws AuthenticationException {
		SecurityUtil.isAuthenticatedUser();
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		List<Employee> allEmployee = this.employeeService.getAllEmployee(paging);
		Map<String, Object> map = new HashMap<>();
		if (!allEmployee.isEmpty()) {
			map.put(ConstantValues.MESSAGE, ConstantValues.DATA_FETCHED_SUCCESS_TEXT);
			map.put(ConstantValues.STATUS_CODE, ConstantValues.SUCCESS_MESSAGE);
			map.put(ConstantValues.STATUS_TEXT, HttpStatus.OK.value());
			map.put(ConstantValues.RESULT, allEmployee);
		} else {
			map.put(ConstantValues.MESSAGE, ConstantValues.NO_DATA_FETCHED_SUCCESS_TEXT);
			map.put(ConstantValues.STATUS_CODE, ConstantValues.SUCCESS_MESSAGE);
			map.put(ConstantValues.STATUS_TEXT, HttpStatus.OK.value());
			map.put(ConstantValues.RESULT, allEmployee);
		}
		return ResponseEntity.ok(map);
	}

	@PostMapping("/change-password")
	public ResponseEntity<Map<String, Object>> changePassword(@Valid @RequestBody ChangePasswordDto passwordDto)
			throws AuthenticationException {

		return ResponseEntity.ok(this.employeeService.changePassword(passwordDto));

	}

	@GetMapping("/get-employee/{empId}")
	public ResponseEntity<Map<String, Object>> getEmployeeById(@PathVariable long empId) {
		return ResponseEntity.ok().body(this.employeeService.getEmployeeById(empId));
	}

	@GetMapping("/search-employee/{empId}")
	public ResponseEntity<Map<String, Object>> searchEmployeeById(@PathVariable long empId) {

		return ResponseEntity.ok().body(employeeService.searchEmployeeById(empId));
	}

	@PostMapping("/add-shift-timing")
	public ResponseEntity<Map<String, Object>> addShiftTimings(@RequestBody ShiftTimingReqDto shiftTimingReqDto)
			throws AuthenticationException {
		if(shiftTimingReqDto.getWeekOff().length > 2)
			throw new ResourceNotProcessedException("WeekOff can only be used on two days.");
		
		shiftTimingReqDto.setEmpId(Long.parseLong(SecurityUtil.getCurrentUserDetails().getUsername()));
		return ResponseEntity.ok().body(this.employeeService.addShiftTiming(shiftTimingReqDto));
	}

	@PostMapping("/add-work-location")
	public ResponseEntity<Map<String, Object>> addWorkLocation(
			@RequestBody EmpWorkLocationReqDto empWorkLocationReqDto) throws NumberFormatException, AuthenticationException {
		return ResponseEntity.ok().body(this.employeeService.addWorkLocation(empWorkLocationReqDto));
	}

	@PostMapping("/add-reporting-manager")
	public ResponseEntity<Map<String, Object>> addReportingManager(@RequestBody ManagerRequestDto managerRequestDto)
			throws AuthenticationException {

		if (managerRequestDto.getEmpId() == managerRequestDto.getManagerId())
			throw new ResourceNotProcessedException("Employee and manager should not be the same");

		return ResponseEntity.ok().body(this.employeeService.addReportingManager(managerRequestDto));
	}

	@PostMapping("/access/add-shift-timing")
	public ResponseEntity<Map<String, Object>> addShiftTimingsAccLvl(@RequestBody ShiftTimingReqDto shiftTimingReqDto)
			throws AuthenticationException {

		if(shiftTimingReqDto.getWeekOff().length > 2)
			throw new ResourceNotProcessedException("WeekOff can only be used on two days.");
		return ResponseEntity.ok().body(this.employeeService.addShiftTiming(shiftTimingReqDto));
		
	}

	@PostMapping("/add-about")
	public ResponseEntity<Map<String, Object>> addAbout(@RequestParam String about) throws AuthenticationException {

		return ResponseEntity.ok().body(this.employeeService.addAbout(about));
	}

}
