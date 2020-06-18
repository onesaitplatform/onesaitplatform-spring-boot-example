package com.minsait.onesait.platform.spring.boot.demo.service;

import java.util.Date;
import java.util.List;

import com.minsait.onesait.platform.spring.boot.demo.dto.MessageDTO;
import com.minsait.onesait.platform.spring.boot.demo.dto.StatusAggregationResults;
import com.minsait.onesait.platform.spring.boot.demo.dto.TypeAggregationResults;
import com.minsait.onesait.platform.spring.boot.demo.model.Message;

public interface MessageService {

	List<Message> getAllMessages();

	List<Message> getAllMessages(int limit, int offset);

	Message getMessage(String idMessage);

	void updateMessage(Message message);

	void createMessage(Message message);

	void createMessage(MessageDTO message);

	void createMessage(String message);

	void deleteMessage(String idMessage);

	List<TypeAggregationResults> getMessageAggregationByType();

	List<StatusAggregationResults> getMessageAggregationByStatus();

	List<StatusAggregationResults> getMessageAggregationByStatusPercentage();

	List<Message> getMessagesToUser(String toMessage);

	List<Message> getMessagesFromUser(String fromMessage);

	int updateMessageSent(String idMessage, Date date);

	int updateMessageError(String idMessage, Date date, String errorOnSent);

}
