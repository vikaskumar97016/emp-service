package com.eidiko.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpWorkLocationReqDto {

    private long empId;
    private Date startDate;
    private Date endDate;
    private String workingFrom;
    private String location;



}
