package com.eidiko.employee.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Employee implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private long empId;
	private String empName;
	private String emailId;
	private Date dateOfJoining;
	private Timestamp modifiedDate;
	private String contactNo;
	private long createdBy;
	private boolean isDeleted;
	private String status;

	private String about;

	@JsonIgnore
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<EmpRoleMapping> empRoles = new HashSet<>();

	@JsonIgnore
	@OneToOne(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private LoginDetails loginDetails;

	@JsonIgnore
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<EmpAccessLvlMapping> accessLvl = new HashSet<>();


	@JsonIgnore
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<ReportingManager> reportingManagerForEmployee = new HashSet<>();


	@JsonIgnore
	@OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<ReportingManager> reportingManagerForManager = new HashSet<>();

	
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<EmployeeWorkingLocation> employeeWorkingLocations = new HashSet<>();
	

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<EmpShiftTimings> empShiftTimings = new HashSet<>();
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.empRoles.stream().map(role -> new SimpleGrantedAuthority(role.getRoles().getRoleName()))
				.collect(Collectors.toList());
	}

	@Override
	public String getUsername() {

		return String.valueOf(empId);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return isDeleted;
	}

	public Employee() {
		super();

	}

	public long getEmpId() {
		return empId;
	}

	public void setEmpId(long empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}



	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<EmpRoleMapping> getEmpRoles() {
		return empRoles;
	}

	public void setEmpRoles(Set<EmpRoleMapping> empRoles) {
		this.empRoles = empRoles;
	}

	public LoginDetails getLoginDetails() {
		return loginDetails;
	}

	public void setLoginDetails(LoginDetails loginDetails) {
		this.loginDetails = loginDetails;
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return loginDetails.getPassword();
	}

	public Set<EmpAccessLvlMapping> getAccessLvl() {
		return accessLvl;
	}

	public void setAccessLvl(Set<EmpAccessLvlMapping> accessLvl) {
		this.accessLvl = accessLvl;
	}

	public boolean isDeleted() {
		return isDeleted;
	}


	public Set<ReportingManager> getReportingManagerForEmployee() {
		return reportingManagerForEmployee;
	}

	public void setReportingManagerForEmployee(Set<ReportingManager> reportingManagerForEmployee) {
		this.reportingManagerForEmployee = reportingManagerForEmployee;
	}


	public Set<ReportingManager> getReportingManagerForManager() {
		return reportingManagerForManager;
	}

	public void setReportingManagerForManager(Set<ReportingManager> reportingManagerForManager) {
		this.reportingManagerForManager = reportingManagerForManager;
	}

	public Set<EmployeeWorkingLocation> getEmployeeWorkingLocations() {
		return employeeWorkingLocations;
	}

	public void setEmployeeWorkingLocations(Set<EmployeeWorkingLocation> employeeWorkingLocations) {
		this.employeeWorkingLocations = employeeWorkingLocations;
	}

	
	
	
	
	public Set<EmpShiftTimings> getEmpShiftTimings() {
		return empShiftTimings;
	}

	public void setEmpShiftTimings(Set<EmpShiftTimings> empShiftTimings) {
		this.empShiftTimings = empShiftTimings;
	}


	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName + ", emailId=" + emailId + ", dateOfJoining="
				+ dateOfJoining + ", modifiedDate=" + modifiedDate + ", contactNo=" + contactNo + ", createdBy="
				+ createdBy + ", isDeleted=" + isDeleted + ", status=" + status + ", empRoles=" + empRoles
				+ ", loginDetails=" + loginDetails + "]";
	}

}
