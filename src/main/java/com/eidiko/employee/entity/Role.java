package com.eidiko.employee.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;




@Entity
public class Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private int roleId;
	private String roleName;
	private String roleDesc;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "roles")
	private Set<EmpRoleMapping> employeeRoles = new HashSet<>();

	public Role(int roleId, String roleName, String roleDesc, Set<EmpRoleMapping> employeeRoles) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleDesc = roleDesc;
		this.employeeRoles = employeeRoles;
	}

	public Role() {
		super();
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Set<EmpRoleMapping> getEmployeeRoles() {
		return employeeRoles;
	}

	public void setEmployeeRoles(Set<EmpRoleMapping> employeeRoles) {
		this.employeeRoles = employeeRoles;
	}
	
	
	
	
}
