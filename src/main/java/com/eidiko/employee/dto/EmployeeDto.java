package com.eidiko.employee.dto;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import com.eidiko.employee.entity.EmpRoleMapping;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

	
	private long empId;
	@NotBlank
	private String empName;
	@Email
	private String emailId;
	@PastOrPresent
	private Timestamp dateOfJoining;
	private Timestamp modifiedDate;
	private String contactNo;
	private long createdBy;
	private boolean isDeleted;
	private String status;

	private long managerId;
	
	private String employeeWorkingLocation;
	
	private String location;

	private String shiftTiming;

}
