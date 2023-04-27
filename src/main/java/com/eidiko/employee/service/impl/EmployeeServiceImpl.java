package com.eidiko.employee.service.impl;

import java.security.SecureRandom;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.security.sasl.AuthenticationException;

import com.eidiko.employee.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eidiko.employee.config.SecurityUtil;
import com.eidiko.employee.entity.EmpAccessLvlMapping;
import com.eidiko.employee.entity.EmpRoleMapping;
import com.eidiko.employee.entity.EmpShiftTimings;
import com.eidiko.employee.entity.Employee;
import com.eidiko.employee.entity.EmployeeAccessLevel;
import com.eidiko.employee.entity.EmployeeWorkingLocation;
import com.eidiko.employee.entity.LoginDetails;
import com.eidiko.employee.entity.ReportingManager;
import com.eidiko.employee.exception.PasswordNotValidException;
import com.eidiko.employee.exception.ResourceAlreadyPresentException;
import com.eidiko.employee.exception.ResourceNotFoundException;
import com.eidiko.employee.exception.ResourceNotProcessedException;
import com.eidiko.employee.exception.TokenNotValidException;
import com.eidiko.employee.helper.ConstantValues;
import com.eidiko.employee.helper.Helper;
import com.eidiko.employee.helper.ReadingTemplates;
import com.eidiko.employee.mail.MailService;
import com.eidiko.employee.repo.EmpAccessLvlRepo;
import com.eidiko.employee.repo.EmployeeRepo;
import com.eidiko.employee.repo.RoleRepo;
import com.eidiko.employee.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MailService mailService;

	@Autowired
	private ReadingTemplates readingTemplates;

	@Autowired
	private EmpAccessLvlRepo empAccessLvlRepo;

	@Autowired
	private Helper helper;

	@Override
	public Employee userByUserName(String userName) {
		return this.employeeRepo.findById(Long.parseLong(userName))
				.orElseThrow(() -> new ResourceNotFoundException(ConstantValues.USER_NOT_FOUND_WITH_THIS_ID + userName));
	}

	@Override
	public Map<String, Object> forgotPassword(long empId) {

		Employee employee = this.employeeRepo.findById(empId)
				.orElseThrow(() -> new ResourceNotFoundException(ConstantValues.USER_NOT_FOUND_WITH_THIS_ID + empId));
		Map<String, Object> map = new HashMap<>();

		LoginDetails details = employee.getLoginDetails();
		String password = generateRandomPassword(10, 48, 122);
		if (details != null) {
			details.setPassword(passwordEncoder.encode(password));
		} else {
			LoginDetails loginDetails = new LoginDetails();
			loginDetails.setPassword(passwordEncoder.encode(password));
			loginDetails.setStatus(ConstantValues.ACTIVE_FLAG);
			details = loginDetails;
		}
		details.setEmployee(employee);
		employee.setLoginDetails(details);
		String text = readingTemplates.generateForgotPasswordMailtext(ConstantValues.FORGOT_PASSWORD_MAIL_TEMPLATE_FILE,
				employee.getEmpName(), password);
		EmailDetailsDto detailsDto = new EmailDetailsDto(employee.getEmailId(), text,
				ConstantValues.FORGOT_PASSWORD_MAIL_SUBJECT, null);
		if (this.mailService.sendSimpleMail(detailsDto)) {
			Employee updatedEmployee = this.employeeRepo.save(employee);
			try {
				map.put(ConstantValues.MESSAGE, ConstantValues.PASSWORD_SENT_TEXT_MAIL);
				map.put(ConstantValues.STATUS_CODE, ConstantValues.SUCCESS_MESSAGE);
				map.put(ConstantValues.STATUS_TEXT, HttpStatus.OK);
			}
			catch(Exception e){
				throw new ResourceNotProcessedException(ConstantValues.PASSWORD_NOT_UPDATED_PLEASE_TRY_AGAIN);
			}
		}

		return map;
	}

	public static String generateRandomPassword(int len, int randNumOrigin, int randNumBound) {
		SecureRandom random = new SecureRandom();
		return random.ints(randNumOrigin, randNumBound + 1)
				.filter(i -> Character.isAlphabetic(i) || Character.isDigit(i)).limit(len)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
	}

	@Override
	public Map<String, Object> createEmployee(Employee employee) throws AuthenticationException {
		Employee emp = this.employeeRepo.findById(employee.getEmpId()).orElse(null);
		if (emp != null) {
			throw new ResourceAlreadyPresentException(ConstantValues.EMPLOYEE_IS_ALREADY_PRESENT_WITH_ID + employee.getEmpId());
		}

		// Role Setting Default

		Set<EmpRoleMapping> empRoleMappings = new HashSet<>();

		EmpRoleMapping empRoleMapping = new EmpRoleMapping();
		empRoleMapping.setEmployee(employee);
		empRoleMapping.setRoles(this.roleRepo.findById(ConstantValues.EMPLOYEE_ROLE)
				.orElseThrow(() -> new ResourceNotFoundException(ConstantValues.ROLE_NOT_FOUND)));
		empRoleMappings.add(empRoleMapping);
		employee.setEmpRoles(empRoleMappings);
		employee.setDeleted(true);
		employee.setModifiedDate(new Timestamp(System.currentTimeMillis()));
		employee.setStatus(ConstantValues.ACTIVE_FLAG);
		employee.setCreatedBy(Long.parseLong(SecurityUtil.getCurrentUserDetails().getUsername()));
		employee.setAbout(ConstantValues.DEFAULT_ABOUT_TEXT);
		// inserting data to database
		employee = this.employeeRepo.save(employee);

		Map<String, Object> map = new HashMap<>();
		if (!employee.getAuthorities().isEmpty()) {
			map.put(ConstantValues.MESSAGE, ConstantValues.EMPLOYEE_CREATED_SUCCESS_TEXT);
			map.put(ConstantValues.STATUS_CODE, ConstantValues.SUCCESS_MESSAGE);
			map.put(ConstantValues.STATUS_TEXT, HttpStatus.OK.value());
		} else {
			throw new ResourceNotProcessedException(ConstantValues.EMPLOYEE_CREATED_FAIL_TEXT);
		}
		return map;
	}

	@Override
	public List<Employee> getAllEmployee(Pageable pageable) {

		Page<Employee> page = this.employeeRepo.findAllByIsDeleted(true, pageable);
		if (page.hasContent()) {
			return page.getContent();
		}

		return new ArrayList<>();
	}

	@Override
	public Map<String, Object> changePassword(ChangePasswordDto changePasswordDto) throws AuthenticationException {

		// Current user Validation with token
		long empId= SecurityUtil.getCurrentUserDetails().getEmpId();


		// New Password and Confirm Password Check
		Map<String, Object> map = new HashMap<>();
		if (!changePasswordDto.getConfirmPassword().equals(changePasswordDto.getNewPassword())) {
			throw new PasswordNotValidException(ConstantValues.NEW_PASSWORD_AND_CONFIRM_PASSWORD_ARE_NOT_MATCHING);
		}

		Employee employee = this.employeeRepo.findById(empId).orElseThrow(
				() -> new ResourceNotFoundException(ConstantValues.USER_NOT_FOUND_WITH_THIS_ID + empId));

		LoginDetails details = employee.getLoginDetails();

		// Old Password Check
		if (passwordEncoder.matches(changePasswordDto.getOldPassword(), details.getPassword())) {

			details.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
			employee.setLoginDetails(details);

			try{
				this.employeeRepo.save(employee);
				map.put(ConstantValues.MESSAGE, ConstantValues.PASSWORD_UPDATED_TEXT);
				map.put(ConstantValues.STATUS_CODE, ConstantValues.SUCCESS_MESSAGE);
				map.put(ConstantValues.STATUS_TEXT, HttpStatus.OK.value());
			} catch(Exception e) {
				throw new PasswordNotValidException(ConstantValues.PASSWORD_NOT_UPDATED_PLEASE_TRY_AGAIN);
			}

		} else {
			throw new PasswordNotValidException(ConstantValues.OLD_PASSWORD_IS_NOT_MATCHING);
		}

		return map;
	}

	@Override
	public Map<String, Object> getEmployeeById(long empId) {

		Employee employee = this.employeeRepo.findById(empId)
				.orElseThrow(() -> new ResourceNotFoundException(ConstantValues.USER_NOT_FOUND_WITH_THIS_ID + empId));
		Set<ReportingManager> managerForEmployee = employee.getReportingManagerForEmployee();
		Set<ReportingManager> managerForManager = employee.getReportingManagerForManager();

		Set<ReportingManagerDto> managers = new HashSet<>();
		Set<ReportingManagerDto> employees = new HashSet<>();

		for (ReportingManager m : managerForEmployee) {

			ReportingManagerDto managerDto = new ReportingManagerDto(m.getReportingManagerId(), m.getModifiedDate(),
					m.getStartDate(), m.getEndDate(), m.getManager().getEmpId(), m.getManager().getEmpName());
			managers.add(managerDto);
		}
		for (ReportingManager m : managerForManager) {
			ReportingManagerDto managerDto = new ReportingManagerDto(m.getReportingManagerId(), m.getModifiedDate(),
					m.getStartDate(), m.getEndDate(), m.getEmployee().getEmpId(), m.getEmployee().getEmpName());
			employees.add(managerDto);
		}

		Map<String, Object> map = new HashMap<>();
		map.put(ConstantValues.RESULT, employee);
		map.put(ConstantValues.REPORTING_MANAGERS, managers);
		map.put(ConstantValues.REPORTED_EMPLOYEES, employees);

		return map;
	}

	@Override
	public Map<String, Object> searchEmployeeById(long empId) {

		Employee employee = this.employeeRepo.findById(empId)
				.orElseThrow(() -> new ResourceNotFoundException(ConstantValues.USER_NOT_FOUND_WITH_THIS_ID));
		Map<String, Object> map = new HashMap<>();
		map.put(ConstantValues.EMP_ID, employee.getEmpId());
		map.put(ConstantValues.MESSAGE, ConstantValues.EMPLOYEE_FETCHED_SUCCESSFULLY);
		map.put(ConstantValues.AUTHORITIES, employee.getAuthorities().stream().map(GrantedAuthority::getAuthority));
		map.put(ConstantValues.DESIGNATION, "");
		map.put(ConstantValues.STATUS_CODE, ConstantValues.SUCCESS_MESSAGE);
		map.put(ConstantValues.EMAIL_ID, employee.getEmailId());
		map.put(ConstantValues.USER_NAME, employee.getEmpName());
		map.put(ConstantValues.STATUS_TEXT, HttpStatus.OK.value());
		return map;

	}

	@Override
	public Map<String, Object> addShiftTiming(ShiftTimingReqDto shiftTimingReqDto) throws NumberFormatException, AuthenticationException {

		long empId = shiftTimingReqDto.getEmpId();
		Employee employee = this.employeeRepo.findById(empId).orElseThrow(()->new ResourceNotFoundException(ConstantValues.USER_NOT_FOUND_WITH_THIS_ID+empId));
		Set<EmpShiftTimings> timings= employee.getEmpShiftTimings();

		timings.forEach(timing->{
			if(timing.getEndDate() == null){
				timing.setEndDate(shiftTimingReqDto.getStartDate());
			}
		});
		EmpShiftTimings empShiftTimings = new EmpShiftTimings();
		empShiftTimings.setStartDate(shiftTimingReqDto.getStartDate());
		empShiftTimings.setEndDate(shiftTimingReqDto.getEndDate());
		empShiftTimings.setShiftStartTime(shiftTimingReqDto.getShiftStartTime());
		empShiftTimings.setShiftEndTime(shiftTimingReqDto.getShiftEndTime());
		empShiftTimings.setEmployee(employee);
		empShiftTimings.setWeekOff(String.join(",",shiftTimingReqDto.getWeekOff()));
		empShiftTimings.setModifiedBy(Long.parseLong(SecurityUtil.getCurrentUserDetails().getUsername()));
		timings.add(empShiftTimings);
		employee.setEmpShiftTimings(timings);

		try{
			this.employeeRepo.save(employee);
		} catch(Exception e){
			throw new ResourceNotProcessedException("not updated");
		}
		Map<String, Object > map = new HashMap<>();
		map.put(ConstantValues.STATUS_CODE, ConstantValues.SUCCESS_MESSAGE);
		map.put(ConstantValues.STATUS_TEXT, HttpStatus.CREATED.value());
		map.put(ConstantValues.MESSAGE,"Processed Successfully");
		return map;
	}

	@Override
	public Map<String, Object> addWorkLocation(EmpWorkLocationReqDto empWorkLocationReqDto) throws NumberFormatException, AuthenticationException {

		long empId  = empWorkLocationReqDto.getEmpId();
		Employee employee = this.employeeRepo.findById(empId).orElseThrow(()->new ResourceNotFoundException(ConstantValues.USER_NOT_FOUND_WITH_THIS_ID+empId));
		Set<EmployeeWorkingLocation> employeeWorkingLocations= employee.getEmployeeWorkingLocations();
		employeeWorkingLocations.forEach(wl->{
			if(wl.getEndDate() == null){
				wl.setEndDate(empWorkLocationReqDto.getStartDate());
			}
		});
		EmployeeWorkingLocation workingLocation = new EmployeeWorkingLocation();
		workingLocation.setLocation(empWorkLocationReqDto.getLocation());
		workingLocation.setWorkingFrom(empWorkLocationReqDto.getWorkingFrom());
		workingLocation.setStartDate(empWorkLocationReqDto.getStartDate());
		workingLocation.setEndDate(empWorkLocationReqDto.getEndDate());
		workingLocation.setModifiedBy(Long.parseLong(SecurityUtil.getCurrentUserDetails().getUsername()));
		workingLocation.setEmployee(employee);

		employeeWorkingLocations.add(workingLocation);
		employee.setEmployeeWorkingLocations(employeeWorkingLocations);
		try{
			this.employeeRepo.save(employee);
		}catch(Exception e){
			throw new ResourceNotProcessedException("not updated");
		}
		Map<String, Object > map = new HashMap<>();
		map.put(ConstantValues.STATUS_CODE, ConstantValues.SUCCESS_MESSAGE);
		map.put(ConstantValues.STATUS_TEXT, HttpStatus.CREATED.value());
		map.put(ConstantValues.MESSAGE,"Processed Successfully");
		return map;
	}

	@Override
	public Map<String, Object> addReportingManager(ManagerRequestDto managerRequestDto) throws AuthenticationException {

		long empId=managerRequestDto.getEmpId();
		
		Employee employee=this.employeeRepo.findById(empId).orElseThrow(()->new ResourceNotFoundException(ConstantValues.USER_NOT_FOUND_WITH_THIS_ID+empId));
		Employee manager=this.employeeRepo.findById(managerRequestDto.getManagerId()).orElseThrow(()->new ResourceNotFoundException(ConstantValues.MANAGER_NOT_FOUND_WITH_ID+empId));

		Set<ReportingManager> reportingManagers=employee.getReportingManagerForEmployee();

		reportingManagers.forEach(rm->{
			if(rm.getEndDate() == null){
				rm.setEndDate(managerRequestDto.getStartDate());
			}
		});

		ReportingManager reportingManager=new ReportingManager();
		
		reportingManager.setManager(manager);
		reportingManager.setStartDate(managerRequestDto.getStartDate());
		reportingManager.setEndDate(managerRequestDto.getEndDate());
		reportingManager.setEmployee(employee);
		reportingManager.setModifiedDate(new Timestamp(System.currentTimeMillis()));
		reportingManager.setModifiedBy(Long.parseLong(SecurityUtil.getCurrentUserDetails().getUsername()));

		reportingManagers.add(reportingManager);
		employee.setReportingManagerForEmployee(reportingManagers);
		try{
			this.employeeRepo.save(employee);
		}catch(Exception e){
			e.printStackTrace();
			throw new ResourceNotProcessedException("not updated");
		}
		Map<String, Object > map = new HashMap<>();
		map.put(ConstantValues.STATUS_CODE, ConstantValues.SUCCESS_MESSAGE);
		map.put(ConstantValues.STATUS_TEXT, HttpStatus.CREATED.value());
		map.put(ConstantValues.MESSAGE,"Processed Successfully");
		return map;
	}

	@Override
	public Map<String, Object> addAbout(String about) throws AuthenticationException {


		Employee employee = this.employeeRepo.findById(Long.parseLong(SecurityUtil.getCurrentUserDetails().getUsername())).orElseThrow(
				()->new ResourceNotFoundException("ConstantValues.USER_NOT_FOUND_WITH_THIS_ID")
		);
Map<String,Object> map = new HashMap<>();
		employee.setAbout(about);
		try {
			this.employeeRepo.save(employee);
		}catch (Exception e){
			throw new ResourceNotProcessedException("not updated");
		}
		map.put(ConstantValues.STATUS_CODE, ConstantValues.SUCCESS_MESSAGE);
		map.put(ConstantValues.STATUS_TEXT, HttpStatus.OK.value());
		map.put(ConstantValues.MESSAGE,"Processed Successfully");

		return map;
	}
}
