package com.eidiko.employee.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eidiko.employee.entity.Role;

public interface RoleRepo extends JpaRepository<Role,Integer>{

}
