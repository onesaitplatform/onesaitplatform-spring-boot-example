package com.minsait.onesait.platform.spring.boot.demo.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "Message")
@Getter
@Setter
@JsonRootName("Message")
public class Message {

	public enum MessageType {
		MAIL, SMS
	}

	public enum MessageStatus {
		PENDING, SENT, ERROR
	}

	@Id
	private String idMessage;

	private String txtMessage;

	private MessageType typeMessage;

	private String fromMessage;

	private String toMessage;

	private MessageStatus statusMessage;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date sentDate;

	private String errorOnSending;
}
