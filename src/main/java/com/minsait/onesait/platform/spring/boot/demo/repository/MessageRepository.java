package com.minsait.onesait.platform.spring.boot.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.minsait.onesait.platform.client.springboot.aspect.notifier.OPNotifierOperation;
import com.minsait.onesait.platform.client.springboot.aspect.notifier.OPValidateSchema;
import com.minsait.onesait.platform.spring.boot.demo.model.Message;

public interface MessageRepository extends MongoRepository<Message, String>, MessageRepositoryCustom {

	@OPNotifierOperation(ontology = "Message", async = false)
	Message findByIdMessage(String idMessage);

	@Override
	@OPNotifierOperation(ontology = "Message", async = true)
	List<Message> findAll();

	@Override
	Message save(@OPValidateSchema Message entity);

	@OPNotifierOperation(ontology = "Message", async = true)
	List<Message> findByToMessage(String toMessage);

	@OPNotifierOperation(ontology = "Message", async = true)
	List<Message> findByFromMessage(String fromMessage);

	@OPNotifierOperation(ontology = "Message", async = true)
	List<Message> findByStatusMessage(String statusMessage);

}
