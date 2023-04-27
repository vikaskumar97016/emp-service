package com.eidiko.employee.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eidiko.employee.config.JwtTokenHelper;
import com.eidiko.employee.dto.LoginRequestDto;
import com.eidiko.employee.helper.ConstantValues;
import com.eidiko.employee.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private EmployeeService employeeService;


	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequestDto loginReq){

		this.authenticate(loginReq.getUsername(), loginReq.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(loginReq.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);
		Map<String, Object> map = new HashMap<>();
		map.put("token", token);
		map.put(ConstantValues.TOKEN_EXPIRE_TIME, this.jwtTokenHelper.getExpirationDateFromToken(token).toString());
		map.put(ConstantValues.TOKEN_EXPIRE_TIME_IN_MILLS, this.jwtTokenHelper.getExpirationDateFromToken(token).getTime());
		map.put(ConstantValues.MESSAGE, ConstantValues.SUCCESS_MESSAGE);
		map.put(ConstantValues.STATUS_CODE, ConstantValues.SUCCESS_MESSAGE);
		map.put(ConstantValues.USER_NAME, userDetails.getUsername());
		map.put(ConstantValues.USER_ROLE, userDetails.getAuthorities().stream().map(auth -> auth.getAuthority()));

		return ResponseEntity.ok().body(map);
	}

	private void authenticate(String username, String password){

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		try {

			this.authenticationManager.authenticate(authenticationToken);

		} catch (DisabledException e) {
			throw new DisabledException(ConstantValues.USER_IS_DISABLED);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException(ConstantValues.BAD_CREDENTIALS);
		}
	}

	@PostMapping("forgot-password")
	public ResponseEntity<Map<String, Object>> forgotPassword(@RequestParam String username) throws Exception {
		long empId = Long.parseLong(username);
		Map<String, Object> map = this.employeeService.forgotPassword(empId);
		return ResponseEntity.ok().body(map);
	}

}
