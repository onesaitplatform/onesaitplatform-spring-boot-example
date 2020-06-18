package com.minsait.onesait.platform.spring.boot.demo.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.minsait.onesait.platform.spring.boot.demo.dto.MessageDTO;
import com.minsait.onesait.platform.spring.boot.demo.dto.StatusAggregationResults;
import com.minsait.onesait.platform.spring.boot.demo.dto.TypeAggregationResults;
import com.minsait.onesait.platform.spring.boot.demo.model.Message;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Message REST service", tags = { "Message" })
@RequestMapping("api/messages")
public interface MessageRest {

	@PostMapping
	@ApiOperation(httpMethod = "POST", value = "New message")
	public ResponseEntity<String> createFromDTO(@RequestBody MessageDTO message);

	@PostMapping("raw")
	@ApiOperation(httpMethod = "POST", value = "New message")
	public ResponseEntity<String> createRaw(@RequestBody String message);

	@GetMapping
	@ApiOperation("Get all messages")
	@ApiResponses(@ApiResponse(response = Message[].class, code = 200, message = "OK"))
	public ResponseEntity<List<Message>> findAllMessages(
			@ApiParam(required = false, value = "limit") @RequestParam(required = false, value = "limit") Integer limit,
			@ApiParam(required = false, value = "offset") @RequestParam(required = false, value = "offset") Integer offset);

	@PutMapping("/{idMessage}")
	@ApiOperation("Updates a message")
	public ResponseEntity<String> update(@RequestBody Message message, @PathVariable("idMessage") String idMessage);

	@DeleteMapping("/{idMessage}")
	@ApiOperation("Deletes a message")
	public ResponseEntity<String> delete(@PathVariable("idMessage") String idMessage);

	@GetMapping("/{idMessage}")
	@ApiOperation("Get one Message")
	@ApiResponses(@ApiResponse(response = Message.class, code = 200, message = "OK"))
	public ResponseEntity<Message> findMessage(@PathVariable("idMessage") String idMessage);

	@PostMapping("/{idMessage}/error")
	@ApiOperation("Update a message with error description")
	public ResponseEntity<String> updateWithError(@PathVariable("idMessage") String idMessage,
			@ApiParam(required = true, value = "Description of the error") @RequestParam("description") String description);

	@PostMapping("/{idMessage}/success")
	@ApiOperation("Update a message upon success")
	public ResponseEntity<String> updateSuccess(@PathVariable("idMessage") String idMessage);

	@GetMapping("/to/{user}")
	@ApiOperation("Get all messages to destination")
	@ApiResponses(@ApiResponse(response = Message[].class, code = 200, message = "OK"))
	public ResponseEntity<List<Message>> findAllMessagesTo(@PathVariable("to") String to);

	@GetMapping("/from/{user}")
	@ApiOperation("Get all messages from user")
	@ApiResponses(@ApiResponse(response = Message[].class, code = 200, message = "OK"))
	public ResponseEntity<List<Message>> findAllMessagesFrom(@PathVariable("to") String from);

	@GetMapping("/aggregation/status")
	@ApiOperation("Get aggregated messages by status")
	@ApiResponses(@ApiResponse(response = StatusAggregationResults[].class, code = 200, message = "OK"))
	public ResponseEntity<List<StatusAggregationResults>> aggResultsByStatus(
			@RequestParam(value = "percentage", required = false) Boolean percentage);

	@GetMapping("/aggregation/type")
	@ApiOperation("Get aggregated messages by type")
	@ApiResponses(@ApiResponse(response = TypeAggregationResults[].class, code = 200, message = "OK"))
	public ResponseEntity<List<TypeAggregationResults>> aggResultsByType();

}
