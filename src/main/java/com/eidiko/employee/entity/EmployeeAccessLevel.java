package com.eidiko.employee.entity;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "ACCESS_LVL")
public class EmployeeAccessLevel implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	
	@Id
	private int accessLvlId;
	private String accessLvlName;
	private String accessLvlDesc;
	
	
	@OneToMany(mappedBy = "accessLevel", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<EmpAccessLvlMapping> accessLvl;
	
	
	
	
	
}
