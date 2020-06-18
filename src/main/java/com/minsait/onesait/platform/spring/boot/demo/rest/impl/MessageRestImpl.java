package com.minsait.onesait.platform.spring.boot.demo.rest.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RestController;

import com.minsait.onesait.platform.spring.boot.demo.dto.MessageDTO;
import com.minsait.onesait.platform.spring.boot.demo.dto.StatusAggregationResults;
import com.minsait.onesait.platform.spring.boot.demo.dto.TypeAggregationResults;
import com.minsait.onesait.platform.spring.boot.demo.model.Message;
import com.minsait.onesait.platform.spring.boot.demo.rest.MessageRest;
import com.minsait.onesait.platform.spring.boot.demo.service.MessageService;

@RestController
public class MessageRestImpl implements MessageRest {

	@Autowired
	private MessageService messageService;

	@Override
	public ResponseEntity<String> createFromDTO(MessageDTO message) {
		Assert.notNull(message.getIdMessage(), "Message needs an id");
		messageService.createMessage(message);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> createRaw(String message) {
		messageService.createMessage(message);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<List<Message>> findAllMessages(Integer limit, Integer offset) {
		List<Message> messages;
		if (limit != null && offset != null)
			messages = messageService.getAllMessages(limit, offset);
		else
			messages = messageService.getAllMessages();
		return ResponseEntity.ok().body(messages);
	}

	@Override
	public ResponseEntity<String> update(Message message, String idMessage) {
		if (message.getIdMessage() == null)
			message.setIdMessage(idMessage);
		messageService.updateMessage(message);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<String> delete(String idMessage) {
		messageService.deleteMessage(idMessage);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Message> findMessage(String idMessage) {
		final Message msg = messageService.getMessage(idMessage);
		if (msg == null)
			return ResponseEntity.notFound().build();
		else
			return ResponseEntity.ok().body(msg);
	}

	@Override
	public ResponseEntity<String> updateWithError(String idMessage, String description) {
		final int updated = messageService.updateMessageError(idMessage, new Date(), description);
		return ResponseEntity.ok().body("{\"updated\":" + updated + "}");
	}

	@Override
	public ResponseEntity<String> updateSuccess(String idMessage) {
		final int updated = messageService.updateMessageSent(idMessage, new Date());
		return ResponseEntity.ok().body("{\"updated\":" + updated + "}");
	}

	@Override
	public ResponseEntity<List<Message>> findAllMessagesTo(String to) {
		final List<Message> messages = messageService.getMessagesToUser(to);
		return new ResponseEntity<>(messages, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Message>> findAllMessagesFrom(String from) {
		final List<Message> messages = messageService.getMessagesFromUser(from);
		return new ResponseEntity<>(messages, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<StatusAggregationResults>> aggResultsByStatus(Boolean percentage) {
		List<StatusAggregationResults> results;
		if (percentage != null)
			results = messageService.getMessageAggregationByStatusPercentage();
		else
			results = messageService.getMessageAggregationByStatus();
		return new ResponseEntity<>(results, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<TypeAggregationResults>> aggResultsByType() {
		final List<TypeAggregationResults> results = messageService.getMessageAggregationByType();
		return new ResponseEntity<>(results, HttpStatus.OK);
	}

}
