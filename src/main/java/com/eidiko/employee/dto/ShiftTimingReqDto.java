package com.eidiko.employee.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShiftTimingReqDto {


    private long empId;
    private String[] weekOff;
    private Date startDate;
    private Date endDate;
    private Time shiftStartTime;
    private Time shiftEndTime;



}
