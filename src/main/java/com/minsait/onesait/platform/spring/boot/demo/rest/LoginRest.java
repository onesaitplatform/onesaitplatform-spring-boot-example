package com.minsait.onesait.platform.spring.boot.demo.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Login REST service", tags = { "Authentication" })
@RequestMapping("api/login")
public interface LoginRest {

	@PostMapping
	@ApiOperation(httpMethod = "POST", value = "Login")
	public ResponseEntity<?> login(@RequestParam("username") String username,
			@RequestParam("password") String password);

}
