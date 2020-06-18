package com.minsait.onesait.platform.spring.boot.demo.dto;

import com.minsait.onesait.platform.spring.boot.demo.model.Message.MessageType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

	private String idMessage;

	private String txtMessage;

	private MessageType typeMessage;

	private String fromMessage;

	private String toMessage;
}
