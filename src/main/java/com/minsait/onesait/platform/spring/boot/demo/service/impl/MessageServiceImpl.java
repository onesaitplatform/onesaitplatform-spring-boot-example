package com.minsait.onesait.platform.spring.boot.demo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minsait.onesait.platform.spring.boot.demo.dto.StatusAggregationResults;
import com.minsait.onesait.platform.spring.boot.demo.dto.TypeAggregationResults;
import com.minsait.onesait.platform.spring.boot.demo.model.Message;
import com.minsait.onesait.platform.spring.boot.demo.repository.MessageRepository;
import com.minsait.onesait.platform.spring.boot.demo.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
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
	public void updateMessage(Message message) {
		messageRepository.save(message);
	}

	@Override
	public void createMessage(Message message) {
		messageRepository.save(message);

	}

	@Override
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

}
