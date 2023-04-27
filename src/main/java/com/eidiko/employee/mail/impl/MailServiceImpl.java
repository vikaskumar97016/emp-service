package com.eidiko.employee.mail.impl;

import com.eidiko.employee.helper.ConstantValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.eidiko.employee.dto.EmailDetailsDto;
import com.eidiko.employee.exception.ResourceNotProcessedException;
import com.eidiko.employee.mail.MailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean sendSimpleMail(EmailDetailsDto details) {
		try {
			// Creating a simple mail message
			MimeMessage message = javaMailSender.createMimeMessage();
			// Setting up necessary details

			message.setFrom(sender);
			message.setRecipients(MimeMessage.RecipientType.TO, details.getRecipient());
			message.setSubject(details.getSubject());

			message.setContent(details.getMsgBody(), "text/html; charset=utf-8");
			javaMailSender.send(message);
			this.logger.info("<<<<<<<<<<<<<<<<<<<" + ConstantValues.MAIL_SENT + ">>>>>>>>>>>>>>>>>>>>");
			return true;
		} catch (MessagingException | MailException e) {
			throw new ResourceNotProcessedException(e.getMessage());
		} 
		

	}

}
