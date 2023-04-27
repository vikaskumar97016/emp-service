package com.eidiko.employee.helper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;

@Service
public class Helper {

	public Timestamp getStartDate(Timestamp dateOfJoining) {
		
		Timestamp portalStartDate = getTimestampFromatFromString(ConstantValues.PORTAL_STARTING_FROM_DATE);
		if(dateOfJoining.after(portalStartDate))
			return dateOfJoining;

		return portalStartDate;
	}

	public Timestamp getTimestampFromatFromString(String date) {

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(ConstantValues.SIMPLE_DATE_FORMAT);
			java.util.Date parsedDate = dateFormat.parse(date);
			return new java.sql.Timestamp(parsedDate.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
