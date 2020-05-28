package com.minsait.onesait.platform.spring.boot.demo.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.minsait.onesait.platform.spring.boot.demo.rest.LoginRest;
import com.minsait.onesait.platform.web.security.client.domain.OAuth2Token;
import com.minsait.onesait.platform.web.security.client.login.LoginService;

@RestController
public class LoginRestImpl implements LoginRest {

	@Autowired
	private LoginService loginService;

	@Override
	public ResponseEntity<?> login(String username, String password) {
		try {
			final OAuth2Token token = loginService.login(username, password);
			return new ResponseEntity<>(token, HttpStatus.OK);
		} catch (final Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
