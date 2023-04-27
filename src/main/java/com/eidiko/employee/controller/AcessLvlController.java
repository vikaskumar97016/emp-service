package com.eidiko.employee.controller;
import com.eidiko.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/access")
public class AcessLvlController {

    @Autowired
    private EmployeeService employeeService;







}
