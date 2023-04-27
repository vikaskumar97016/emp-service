package com.eidiko.employee.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ManagerRequestDto {

    private long empId;
    private long managerId;
    private Timestamp startDate;
    private Timestamp endDate;
}
