package com.eidiko.employee.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportingManager  {


	@Id	
	private long reportingManagerId;
	private Timestamp modifiedDate;
	private Timestamp startDate;
	private Timestamp endDate;
	private long modifiedBy;
	
	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "empId")
	private Employee employee;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "managerId")
	private Employee manager;


	
	
	
}
