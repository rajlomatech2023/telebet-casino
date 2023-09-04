package com.telebet.telebetauthservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telebet.telebetauthservice.entities.UserVO;
import com.telebet.telebetauthservice.model.Result;
import com.telebet.telebetauthservice.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
	
	Logger logger = LoggerFactory.getLogger(AuthController.class);
	private Result result = new Result();

	@Autowired
	private AuthService authService;
	
	@PostMapping(value = "/register")
	public Result register(@Valid @RequestBody UserVO request){
		String registeringUser = request.getUserName();
		String registeringUserPassword = request.getPassword();
		if(registeringUser.length() > 1 && registeringUserPassword.length() > 1) {
			try {
				result = authService.register(request);
			}catch (Exception e){
				logger.error(e.getMessage());
				result.setResponseCode("");
				result.setResponseMessage(e.getMessage());
				result.setResponseStatus("FAILED");
			}
		} else {
			result.setResponseCode("");
			result.setResponseMessage("Username or password must not be empty");
			result.setResponseStatus("FAILED");
		}
		
		return result;
	}
	
	@PostMapping(value = "/login")
	public Result validateLogin(@RequestBody UserVO request){
		
		return authService.validateLogin(request);
	}
}
