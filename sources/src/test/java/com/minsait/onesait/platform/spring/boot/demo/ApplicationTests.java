package com.minsait.onesait.platform.spring.boot.demo;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.minsait.onesait.platform.client.NotifierClient;
import com.minsait.onesait.platform.client.exception.NotifierException;
import com.minsait.onesait.platform.client.model.Notification;
import com.minsait.onesait.platform.client.model.Notification.Operation;
import com.minsait.onesait.platform.client.model.Notification.QueryType;
import com.minsait.onesait.platform.spring.boot.demo.model.Message;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public class ApplicationTests {
	private static final String ID = "789HJK";

	private final NotifierClient notifierClient = new NotifierClient("http://localhost", "developer", "Changed2019!",
			false);

	private static final String MESSAGE = "Message";

	@Test
	public void a_whenUpdateOrCreateFromClass_thenOntologyIsCreated() {
		final String schema = notifierClient.createOrUpdateOntology(Message.class);
		log.info(schema);
	}

	@Test
	public void whenValidationOnValidJSON_thenValidationSuccess() {
		notifierClient.validateSchema(MESSAGE, ApplicationTestResources.VALID_MESSAGE_JSON);
	}

	@Test(expected = NotifierException.class)
	public void whenValidationOnInvalidJSON_thenValidationFails() {
		notifierClient.validateSchema(MESSAGE, ApplicationTestResources.INVALID_MESSAGE_JSON);
	}

	@Test
	public void whenNotifyingInsert_thenModulesGetNotfied() {
		notifierClient.notify(Notification.builder().id(ID).ontology(MESSAGE).operation(Operation.INSERT)
				.payload(ApplicationTestResources.VALID_MESSAGE_JSON).build());
	}

	@Test
	public void whenNotifyingUpdate_thenModulesGetNotfied() {
		notifierClient.notify(Notification.builder().id(ID).ontology(MESSAGE).operation(Operation.UPDATE)
				.payload(ApplicationTestResources.VALID_MESSAGE_JSON).build());
	}

	@Test
	public void whenNotifyingUpdateByQuery_thenModulesGetNotfied() {
		notifierClient.notify(Notification.builder().id(ID).ontology(MESSAGE).operation(Operation.UPDATE)
				.queryType(QueryType.SQL).query("UPDATE Message SET count =1").build());
	}

	@Test
	public void whenNotifyingDeleteById_thenModulesGetNotfied() {
		notifierClient.notify(Notification.builder().id(ID).ontology(MESSAGE).operation(Operation.DELETE).build());
	}

}
