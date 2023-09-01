package com.telebet.userservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telebet.userservice.entities.User;
import com.telebet.userservice.model.Result;
import com.telebet.userservice.service.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/users")
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/save")
	public String save(@Valid @RequestBody User userVo){
		
		String message = userService.save(userVo);
		
		return message;
	}
	
	@GetMapping("/getUserDetails")
	public User getUser(@RequestHeader(value = "userName") String userName, @RequestHeader(value="password") String password, @RequestHeader(value = "role") String role) {
		
		return userService.getUserDetails(userName, password, role);
	} 
	
	@PostMapping("/validateUser")
	public Result validateLoginDetails(@Valid @RequestBody User userVo){
		
		return userService.validateUserDetails(userVo);
	}
	
	@GetMapping(value= "/secured")
	public ResponseEntity<String> secureEndPoint() {
		return ResponseEntity.ok("Secure endpoint available");
	}
}
