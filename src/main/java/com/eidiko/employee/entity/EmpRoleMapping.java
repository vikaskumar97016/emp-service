package com.eidiko.employee.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;



@Entity
public class EmpRoleMapping implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	private long empRoleId;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "emp_id")
	private Employee employee;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	private Role roles;


	public EmpRoleMapping() {
		super();
	}


	public EmpRoleMapping(long empRoleId, Employee employee, Role roles) {
		super();
		this.empRoleId = empRoleId;
		this.employee = employee;
		this.roles = roles;
	}


	public long getEmpRoleId() {
		return empRoleId;
	}


	public void setEmpRoleId(long empRoleId) {
		this.empRoleId = empRoleId;
	}


	public Employee getEmployee() {
		return employee;
	}


	public void setEmployee(Employee employee) {
		this.employee = employee;
	}


	public Role getRoles() {
		return roles;
	}


	public void setRoles(Role roles) {
		this.roles = roles;
	}
	
	
	

}
