package com.eidiko.employee.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eidiko.employee.entity.EmpRoleMapping;

public interface EmpRoleMappingRepo extends JpaRepository<EmpRoleMapping, Long>{

}
