package com.minsait.onesait.platform.spring.boot.demo.service;

import com.minsait.onesait.platform.spring.boot.demo.exception.SMSException;

public interface SMSService {

	public void sendMessage(String number, String message) throws SMSException;

}
