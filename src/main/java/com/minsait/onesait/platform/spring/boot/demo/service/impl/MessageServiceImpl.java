package com.minsait.onesait.platform.spring.boot.demo.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsait.onesait.platform.client.enums.OperationType;
import com.minsait.onesait.platform.client.springboot.aspect.notifier.OPNotifierOperation;
import com.minsait.onesait.platform.spring.boot.demo.dto.MessageDTO;
import com.minsait.onesait.platform.spring.boot.demo.dto.StatusAggregationResults;
import com.minsait.onesait.platform.spring.boot.demo.dto.TypeAggregationResults;
import com.minsait.onesait.platform.spring.boot.demo.exception.MailException;
import com.minsait.onesait.platform.spring.boot.demo.model.Message;
import com.minsait.onesait.platform.spring.boot.demo.model.Message.MessageStatus;
import com.minsait.onesait.platform.spring.boot.demo.repository.MessageRepository;
import com.minsait.onesait.platform.spring.boot.demo.service.MailService;
import com.minsait.onesait.platform.spring.boot.demo.service.MessageService;
import com.minsait.onesait.platform.spring.boot.demo.service.SMSService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MailService mailService;
	@Autowired
	private SMSService smsService;

	@Autowired
	private MessageRepository messageRepository;

	@Override
	public List<Message> getAllMessages() {
		return messageRepository.findAll();
	}

	@Override
	public List<Message> getAllMessages(int limit, int offset) {
		return messageRepository.findAllWithLimitOffset(limit, offset);
	}

	@Override
	public Message getMessage(String idMessage) {
		return messageRepository.findByIdMessage(idMessage);
	}

	@Override
	@OPNotifierOperation(ontology = "Message", id = "#p0.idMessage", payload = "#p0", operationType = OperationType.UPDATE, async = true)
	public void updateMessage(Message message) {
		messageRepository.save(message);
	}

	@Override
	@OPNotifierOperation(ontology = "Message", operationType = OperationType.INSERT, async = true)
	public void createMessage(Message message) {
		if (!Message.MessageStatus.PENDING.equals(message.getStatusMessage()))
			message.setStatusMessage(MessageStatus.PENDING);
		if (message.getIdMessage() == null) {
			message.setIdMessage(UUID.randomUUID().toString());
		}
		messageRepository.save(message);
		if (Message.MessageType.MAIL.equals(message.getTypeMessage())) {
			try {
				mailService.sendMail(message.getToMessage(), message.getTxtMessage());
				updateMessageSent(message.getIdMessage(), new Date());
			} catch (final MailException e) {
				log.error("Mail service error, could not send mail to {} ", message.getToMessage(), e);
				updateMessageError(message.getIdMessage(), new Date(), e.getMessage());
			}
		} else {
			try {
				smsService.sendMessage(message.getToMessage(), message.getTxtMessage());
				updateMessageSent(message.getIdMessage(), new Date());
			} catch (final Exception e) {
				log.error("SMS service error, could not send SMS to {} ", message.getToMessage(), e);
				updateMessageError(message.getIdMessage(), new Date(), e.getMessage());
			}
		}
	}

	@Override
	@OPNotifierOperation(async = false, ontology = "Message", operationType = OperationType.DELETE, id = "#p0")
	public void deleteMessage(String idMessage) {
		messageRepository.deleteById(idMessage);

	}

	@Override
	public List<TypeAggregationResults> getMessageAggregationByType() {
		return messageRepository.countByType();
	}

	@Override
	public List<StatusAggregationResults> getMessageAggregationByStatus() {
		return messageRepository.countByStatus();
	}

	@Override
	public List<StatusAggregationResults> getMessageAggregationByStatusPercentage() {
		return messageRepository.countByStatusPercentage();
	}

	@Override
	public List<Message> getMessagesToUser(String toMessage) {
		return messageRepository.findByToMessage(toMessage);
	}

	@Override
	public List<Message> getMessagesFromUser(String fromMessage) {
		return messageRepository.findByFromMessage(fromMessage);
	}

	@Override
	public int updateMessageSent(String idMessage, Date date) {
		return messageRepository.updateMessageSent(idMessage, date);

	}

	@Override
	public int updateMessageError(String idMessage, Date date, String errorOnSent) {
		return messageRepository.updateMessageWithError(idMessage, date, errorOnSent);

	}

	@Override
	public void createMessage(MessageDTO message) {
		final Message m = new Message();
		m.setIdMessage(message.getIdMessage());
		m.setStatusMessage(MessageStatus.PENDING);
		m.setTypeMessage(message.getTypeMessage());
		m.setFromMessage(message.getFromMessage());
		m.setToMessage(message.getToMessage());
		m.setTxtMessage(message.getTxtMessage());
		this.createMessage(m);

	}

	@Override
	@OPNotifierOperation(operationType = OperationType.INSERT, payload = "#p0", ontology = "Message")
	public void createMessage(String message) {
		final ObjectMapper mapper = new ObjectMapper();
		try {
			this.createMessage(mapper.readValue(message, Message.class));
		} catch (final Exception e) {
			log.error("Unreadable message");
		}

	}

}
