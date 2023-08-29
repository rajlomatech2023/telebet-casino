package com.telebet.userservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telebet.userservice.entities.User;
import com.telebet.userservice.repository.UserRepository;
import com.telebet.userservice.service.UserService;


@RestController
@RequestMapping(value = "/users")
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired(required = true)
	private UserRepository userRepository;
	
	@PostMapping("/save")
	public ResponseEntity<User> save(@RequestBody User userVo){
		logger.info("userDetails {} ", userVo);
		return ResponseEntity.ok(userRepository.save(userVo));
	}
	
	@GetMapping("/getUserDetails")
	public User getUser(@RequestHeader(value = "userName") String userName, @RequestHeader(value="password") String password, @RequestHeader(value = "role") String role) {
		logger.info("in userController getUserDetails method...");
		User userVo =  userRepository.findUserByUserNameAndPassword(userName, password);
		
		if(userVo == null) {
			throw new RuntimeException("userNot found for the given id: "+userName);
		}
		
		return userVo;
	} 
	
	@GetMapping(value= "/secured")
	public ResponseEntity<String> secureEndPoint() {
		return ResponseEntity.ok("Secure endpoint available");
	}
}
