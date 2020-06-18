package com.minsait.onesait.platform.spring.boot.demo.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.reinert.jjschema.Attributes;
import com.minsait.onesait.platform.client.springboot.aspect.notifier.OPEntity;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "Message")
@Getter
@Setter
@JsonInclude(Include.NON_NULL)
@OPEntity
public class Message {

	public enum MessageType {
		MAIL, SMS
	}

	public enum MessageStatus {
		PENDING, SENT, ERROR
	}

	@Id
	@Attributes(required = true)
	private String idMessage;

	private String txtMessage;

	private MessageType typeMessage;

	@Attributes(required = true)
	private String fromMessage;

	private String toMessage;

	private MessageStatus statusMessage;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date sentDate;

	private String errorOnSent;
}
