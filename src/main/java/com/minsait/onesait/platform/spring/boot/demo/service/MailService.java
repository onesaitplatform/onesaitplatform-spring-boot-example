package com.minsait.onesait.platform.spring.boot.demo.service;

import com.minsait.onesait.platform.spring.boot.demo.exception.MailException;

public interface MailService {
	void sendMail(String to, String text) throws MailException;
}
