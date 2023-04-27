package com.eidiko.employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EidikoEmployeeServiceApplication implements CommandLineRunner{

	@Autowired
	private PasswordEncoder encoder;
	
	static Logger logger = LoggerFactory.getLogger(EidikoEmployeeServiceApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(EidikoEmployeeServiceApplication.class, args);
		logger.info("Application started !!!");		
	}
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(encoder.encode("admin"));
		
	}

}
