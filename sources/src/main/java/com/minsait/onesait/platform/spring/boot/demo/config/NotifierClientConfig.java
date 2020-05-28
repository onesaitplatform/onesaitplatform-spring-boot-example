package com.minsait.onesait.platform.spring.boot.demo.config;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.minsait.onesait.platform.client.NotifierClient;
import com.minsait.onesait.platform.spring.boot.demo.model.Message;

@Configuration
public class NotifierClientConfig {

	private static final String USER = "developer";
	private static final String PASS_WORD = "Changed2019!";
	private static final String SERVER = "http://localhost";
	private static final String API_KEY = "78011c7a6b2d46bab5a2532abdcb3ed2";

	@Bean("notifierClientApiKey")
	@Primary
	public NotifierClient notifierClientApiKey() {
		return new NotifierClient(SERVER, API_KEY, false);
	}

	@Bean("notifierClientApiKey")
	public NotifierClient notifierClientOauth() {
		return new NotifierClient(SERVER, USER, PASS_WORD, false);
	}

	@PostConstruct
	void createOrUpdateEntity() {
		notifierClientApiKey().createOrUpdateOntology(Message.class);
	}
}
