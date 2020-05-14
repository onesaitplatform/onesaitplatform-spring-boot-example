package com.minsait.onesait.platform.spring.boot.demo;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsait.onesait.platform.spring.boot.demo.dto.StatusAggregationResults;
import com.minsait.onesait.platform.spring.boot.demo.repository.MessageRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@RunWith(SpringRunner.class)
@ImportResource(locations = "classpath:/message.json")
@Slf4j
public class ApplicationTests {

	@Autowired
	MessageRepository messageRepository;

	private final ObjectMapper mapper = new ObjectMapper();

	@Test
	public void testAggregation() throws JsonParseException, JsonMappingException, IOException {
		// final Message msg = mapper
		// .readValue(new
		// File(getClass().getClassLoader().getResource("message.json").getFile()),
		// Message.class);
		// messageRepository.save(msg);

		final List<StatusAggregationResults> results = messageRepository.countByStatus();
		final List<StatusAggregationResults> resultsP = messageRepository.countByStatusPercentage();
		log.info("results: {}", results.toString());
	}

}
