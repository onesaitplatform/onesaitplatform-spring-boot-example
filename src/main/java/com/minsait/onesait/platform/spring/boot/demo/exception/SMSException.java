package com.minsait.onesait.platform.spring.boot.demo.exception;

public class SMSException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public SMSException(String msg) {
		super(msg);
	}

	public SMSException(String msg, Throwable e) {
		super(msg, e);
	}

}
