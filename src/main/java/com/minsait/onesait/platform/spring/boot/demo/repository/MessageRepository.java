package com.minsait.onesait.platform.spring.boot.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.minsait.onesait.platform.spring.boot.demo.model.Message;

public interface MessageRepository extends MongoRepository<Message, String>, MessageRepositoryCustom {

	Message findByIdMessage(String idMessage);

	@Override
	List<Message> findAll();

	List<Message> findByToMessage(String toMessage);

	List<Message> findByFromMessage(String fromMessage);

	List<Message> findByStatusMessage(String statusMessage);

}
