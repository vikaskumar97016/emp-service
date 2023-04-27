package com.eidiko.employee.exception;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.security.sasl.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.eidiko.employee.helper.ConstantValues;
import com.fasterxml.jackson.core.JsonParseException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;


@RestControllerAdvice
public class GlobalExceptionHandler {
	


	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ProblemDetail> handleValidationExceptions(MethodArgumentNotValidException ex) {
		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
		List<String> errorMessages = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));
		problemDetail.setProperty(ConstantValues.MESSAGE, String.join(", ", errorMessages));
		problemDetail.setProperty(ConstantValues.STATUS_CODE, ConstantValues.ERROR_MESSAGE);
		problemDetail.setStatus(HttpStatus.BAD_REQUEST);
		problemDetail.setType(ConstantValues.ERROR_MESSAGE_URL);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ProblemDetail> handleResourceNotFoundException(ResourceNotFoundException ex) {

		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

		problemDetail.setProperty(ConstantValues.MESSAGE, ex.getMessage());
		problemDetail.setProperty(ConstantValues.STATUS_CODE, ConstantValues.ERROR_MESSAGE);
		problemDetail.setStatus(HttpStatus.NOT_FOUND);
		problemDetail.setType(ConstantValues.ERROR_MESSAGE_URL);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);

	}
	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<ProblemDetail> handleDisabledException(DisabledException ex) {

		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);

		problemDetail.setProperty(ConstantValues.MESSAGE, ex.getMessage());
		problemDetail.setProperty(ConstantValues.STATUS_CODE, ConstantValues.ERROR_MESSAGE);
		problemDetail.setStatus(HttpStatus.UNAUTHORIZED);
		problemDetail.setType(ConstantValues.ERROR_MESSAGE_URL);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);

	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ProblemDetail> handleBadCredentialsException(BadCredentialsException ex) {

		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);

		problemDetail.setProperty(ConstantValues.MESSAGE, ex.getMessage());
		problemDetail.setProperty(ConstantValues.STATUS_CODE, ConstantValues.ERROR_MESSAGE);
		problemDetail.setStatus(HttpStatus.UNAUTHORIZED);
		problemDetail.setType(ConstantValues.ERROR_MESSAGE_URL);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);

	}
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<ProblemDetail> handleFileNotFoundException(FileNotFoundException ex) {

		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
		problemDetail.setProperty(ConstantValues.MESSAGE, ex.getMessage());
		problemDetail.setProperty(ConstantValues.STATUS_CODE, ConstantValues.ERROR_MESSAGE);
		problemDetail.setStatus(HttpStatus.NOT_FOUND);
		problemDetail.setType(ConstantValues.ERROR_MESSAGE_URL);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);

	}
	@ExceptionHandler(ResourceNotProcessedException.class)
	public ResponseEntity<ProblemDetail> handleResourceNotProcessedException(ResourceNotProcessedException ex) {

		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
		problemDetail.setProperty(ConstantValues.MESSAGE, ex.getMessage());
		problemDetail.setProperty(ConstantValues.STATUS_CODE, ConstantValues.ERROR_MESSAGE);
		problemDetail.setStatus(HttpStatus.NOT_FOUND);
		problemDetail.setType(ConstantValues.ERROR_MESSAGE_URL);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);

	}

	@ExceptionHandler(MailException.class)
	public ResponseEntity<ProblemDetail> handleMailException(MailException ex) {

		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		problemDetail.setProperty(ConstantValues.MESSAGE, ex.getMessage());
		problemDetail.setProperty(ConstantValues.STATUS_CODE, ConstantValues.ERROR_MESSAGE);
		problemDetail.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		problemDetail.setType(ConstantValues.ERROR_MESSAGE_URL);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);

	}
	@ExceptionHandler(ResourceAlreadyPresentException.class)
	public ResponseEntity<ProblemDetail> handleResourceAlreadyPresentException(ResourceAlreadyPresentException ex) {

		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		problemDetail.setProperty(ConstantValues.MESSAGE, ex.getMessage());
		problemDetail.setProperty(ConstantValues.STATUS_CODE, ConstantValues.ERROR_MESSAGE);
		problemDetail.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		problemDetail.setType(ConstantValues.ERROR_MESSAGE_URL);
		problemDetail.setTitle(ConstantValues.USER_IS_ALREADY_PRESENT);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);

	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ProblemDetail> handleAuthenticationException(AuthenticationException ex, HttpServletResponse response) {

		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
		problemDetail.setProperty(ConstantValues.MESSAGE, ex.getMessage());
		problemDetail.setProperty(ConstantValues.STATUS_CODE, ConstantValues.ERROR_MESSAGE);
		problemDetail.setStatus(HttpStatus.UNAUTHORIZED);
		problemDetail.setType(ConstantValues.ERROR_MESSAGE_URL);
		problemDetail.setTitle(ConstantValues.PERMISSION_DENIED);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);

	}
	
	@ExceptionHandler(TokenNotValidException.class)
	public ResponseEntity<ProblemDetail> handleTokenNotValidException(TokenNotValidException ex) {

		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
		problemDetail.setProperty(ConstantValues.MESSAGE, ex.getMessage());
		problemDetail.setProperty(ConstantValues.STATUS_CODE, ConstantValues.ERROR_MESSAGE);
		problemDetail.setStatus(HttpStatus.UNAUTHORIZED);
		problemDetail.setType(ConstantValues.ERROR_MESSAGE_URL);
		problemDetail.setTitle(ConstantValues.PERMISSION_DENIED);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);

	}
	
	@ExceptionHandler(PasswordNotValidException.class)
	public ResponseEntity<ProblemDetail> handlePasswordNotValidException(PasswordNotValidException ex) {

		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
		problemDetail.setProperty(ConstantValues.MESSAGE, ex.getMessage());
		problemDetail.setProperty(ConstantValues.STATUS_CODE, ConstantValues.ERROR_MESSAGE);
		problemDetail.setStatus(HttpStatus.BAD_REQUEST);
		problemDetail.setType(ConstantValues.ERROR_MESSAGE_URL);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);

	}
	
	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<ProblemDetail> handleNumberFormatException(NumberFormatException ex) {

		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
		problemDetail.setProperty(ConstantValues.MESSAGE, ex.getMessage());
		problemDetail.setProperty(ConstantValues.STATUS_CODE, ConstantValues.ERROR_MESSAGE);
		problemDetail.setStatus(HttpStatus.BAD_REQUEST);
		problemDetail.setType(ConstantValues.ERROR_MESSAGE_URL);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);

	}
	
	
	
	
	
	
}
