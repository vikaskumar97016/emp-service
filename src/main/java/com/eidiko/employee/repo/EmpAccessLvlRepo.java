package com.eidiko.employee.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eidiko.employee.entity.EmployeeAccessLevel;

public interface EmpAccessLvlRepo extends JpaRepository<EmployeeAccessLevel, Integer>{

}
