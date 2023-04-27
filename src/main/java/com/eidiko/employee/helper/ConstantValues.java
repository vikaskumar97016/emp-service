package com.eidiko.employee.helper;

import java.net.URI;

public class ConstantValues {

	private ConstantValues() {
	}

	public static final String JWT_SECRET = "eidiko-internal-portal";

	public static final long TOKEN_VALIDITY = 1000 * 60 * 60 * 24;

	public static final URI ERROR_MESSAGE_URL = URI.create("/error");

	public static final String ERROR_MESSAGE = "error";

	public static final String SUCCESS_MESSAGE = "success";

	public static final String MESSAGE = "message";

	public static final String STATUS_CODE = "statusMessage";

	public static final String FORGOT_PASSWORD_MAIL_TEMPLATE_FILE = "forgot-password-mail";

	public static final String ACTIVE_FLAG = "Active";

	public static final String INACTIVE_FLAG = "Inactive";

	public static final String FORGOT_PASSWORD_MAIL_SUBJECT = "Eidiko Portal Password";

	public static final String STATUS_TEXT = "status";

	public static final String PASSWORD_SENT_TEXT_MAIL = "password sent to your mail";
	public static final String PASSWORD_UPDATED_TEXT = "Password Updated Successfully";

	public static final String EMPLOYEE_CREATED_SUCCESS_TEXT = "Employee created Successfully";

	public static final String EMPLOYEE_CREATED_FAIL_TEXT = "Employee not created";

	public static final String DATA_FETCHED_SUCCESS_TEXT = "Data fetched successfully";

	public static final String NO_DATA_FETCHED_SUCCESS_TEXT = "No Data Available";

	public static final String RESULT = "result";

	public static final int EMPLOYEE_ROLE = 101;
	public static final int EMPLOYEE_ACCESS_LEVEL = 201;

	public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss.SSS";
	public static final String PORTAL_STARTING_FROM_DATE = "2023-01-01 00:00:00.0";
	public static final String TOKEN_EXPIRE_TIME = "tokenExpireTime";
	public static final String TOKEN_EXPIRE_TIME_IN_MILLS = "tokenExpireTimeInMills";
	public static final String USER_NAME = "userName";
	public static final String USER_ROLE = "userRole";
	public static final String USER_IS_DISABLED = "User Is Disabled";
	public static final String BAD_CREDENTIALS = "Bad Credentials";
	public static final String USER_NOT_FOUND_WITH_THIS_ID = "User Not Found With This Id";
	public static final String EMPLOYEE_IS_ALREADY_PRESENT_WITH_ID = "Employee Is Already Present";
	public static final String PASSWORD_NOT_UPDATED_PLEASE_TRY_AGAIN = "Password Not Updated! Please Try Again";
	public static final String MANAGER_NOT_FOUND_WITH_ID = "Manager Not Found With Id";
	public static final String ACCESS_LEVEL_NOT_FOUND = "Access Level Not Found";
	public static final String ROLE_NOT_FOUND = "Role Not Found";
	public static final String YOUR_NOT_UNABLE_TO_CHANGE_THE_PASSWORD_FOR_THIS_ID = "You are Not allowed To Change The Password For This Id.";
	public static final String NEW_PASSWORD_AND_CONFIRM_PASSWORD_ARE_NOT_MATCHING = "New Password And Confirm Password Are Not Matching.";
	public static final String OLD_PASSWORD_IS_NOT_MATCHING = "Old Password Is Not Matching";
	public static final String REPORTING_MANAGERS = "reportingManagers";
	public static final String REPORTED_EMPLOYEES = "reportedEmployees";
	public static final String EMP_ID = "empId";
	public static final String EMAIL_ID = "emailId";
	public static final String EMPLOYEE_FETCHED_SUCCESSFULLY = "Employee Fetched Successfully";
	public static final String AUTHORITIES = "Authorities";
	public static final String DESIGNATION = "Designation";
	public static final String PATH = "Path";
	public static final String AUTHORIZATION = "Authorization";
	public static final String JWT_TOKEN_DOES_NOT_BEGIN_WITH_BEARER = "Jwt Token Does Not Begin With Bearer";
	public static final String INVALID_TOKEN_JWT = "Invalid Token Jwt";
	public static final String JWT_TOKEN_NOT_VALID = "Jwt Token Not Valid";
	public static final String GENERATING_JWT_TOKEN_FOR_USERNAME = "Generating Jwt Token For Username{}";
	public static final String STAR = "*";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String ACCEPT = "Accept";
	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final String DELETE = "DELETE";
	public static final String PUT = "PUT";
	public static final String OPTIONS = "OPTIONS";
	public static final String PATTERN = "/**";
	public static final String SESSION_HAS_BEEN_EXPIRED = "Session Has Been Expired";
	public static final String DOCUMENTS_EIDIKO_PORTAL="C:\\eidikoportal\\templates\\";
//	public static final String DOCUMENTS_EIDIKO_PORTAL = "classpath:templates/";
	public static final String EMAIL_TEMPLATE_MAIL_FILE_FOR_FORGOT_PASSWORD_NOT_FOUND = "Email Template Mail File For Forgot Password Not Found";
	public static final String SOMETHING_WENT_WRONG_PLEASE_TRY_AGAIN_AFTER_SOMETIME = "Something Went Wrong! Please Try Again After Sometime";
	public static final String EMP_NAME = "empName";
	public static final String PASSWORD_TEXT = "Password-Text";
	public static final String MAIL_SENT = "Mail Sent";
	public static final String CUSTOM_USER_DETAILS = "Custom User Details";
	public static final String SET_SHIFT_START_TIME = "10:00";
	public static final String SET_SHIFT_END_TIME = "19:00:00";
	public static final String USER_IS_ALREADY_PRESENT = "User Is Already Present";
	public static final String PERMISSION_DENIED = "Permission Denied";
	public static final String CLIENT_LOCATION = "CLIENT_LOCATION";
	public static final String WFO = "WFO";
	public static final String EMPLOYEE_NOT_FOUND_WITH = "Employee Not Found With";
	public static final String BEARER = "Bearer";

	public static final String DEFAULT_ABOUT_TEXT = "Software engineers apply principles and techniques of engineering, mathematics, and computer science to the design, development, and testing of software applications for computers.";

}
