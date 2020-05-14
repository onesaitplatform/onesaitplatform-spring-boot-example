package com.minsait.onesait.platform.spring.boot.demo.exception;

public class MailException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public MailException(String msg) {
		super(msg);
	}

	public MailException(String msg, Throwable e) {
		super(msg, e);
	}

}
