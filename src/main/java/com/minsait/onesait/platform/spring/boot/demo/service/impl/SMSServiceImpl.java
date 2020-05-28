package com.minsait.onesait.platform.spring.boot.demo.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.minsait.onesait.platform.spring.boot.demo.exception.SMSException;
import com.minsait.onesait.platform.spring.boot.demo.service.SMSService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SMSServiceImpl implements SMSService {

	@Value("${twilio.account-sid}")
	private String accountSID;
	@Value("${twilio.auth-token}")
	private String authToken;
	@Value("${twilio.phone-number}")
	private String phoneNumber;

	@PostConstruct
	public void init() {
		Twilio.init(accountSID, authToken);
	}

	@Override
	public void sendMessage(String number, String message) throws SMSException {
		try {
			Message.creator(new PhoneNumber(number), new PhoneNumber(phoneNumber), message).create();
		} catch (final Exception e) {
			log.error("Could not send SMS", e);
			throw new SMSException("Could not send SMS: " + e.getMessage(), e);
		}
	}

}
