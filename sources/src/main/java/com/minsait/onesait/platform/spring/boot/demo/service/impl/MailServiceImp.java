package com.minsait.onesait.platform.spring.boot.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.minsait.onesait.platform.spring.boot.demo.exception.MailException;
import com.minsait.onesait.platform.spring.boot.demo.service.MailService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailServiceImp implements MailService {

	private static final String SUBJECT = "Spring Boot 2 mail service";

	@Autowired
	public JavaMailSender emailSender;

	@Value("${onesaitplatform.mail-from}")
	private String from;

	@Override
	public void sendMail(String to, String text) throws MailException {
		log.debug("Sending message to {} , message: {}", to, text);
		try {
			final SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(to);
			message.setSubject(SUBJECT);
			message.setText(text);
			message.setFrom(from);
			emailSender.send(message);
		} catch (final Exception e) {
			log.error("Could not send email", e);
			throw new MailException(e.getMessage(), e);
		}

	}

}
