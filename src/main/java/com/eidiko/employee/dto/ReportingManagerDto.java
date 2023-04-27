package com.eidiko.employee.dto;

import java.sql.Timestamp;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportingManagerDto {

	private long reportingManagerId;
	private Timestamp modifiedDate;
	private Timestamp startDate;
	private Timestamp endDate;
	
	
	private long empId;
	private String empName;
	
	
}
