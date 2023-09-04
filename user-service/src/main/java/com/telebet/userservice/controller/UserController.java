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
import com.telebet.userservice.model.Result;
import com.telebet.userservice.service.UserService;


@RestController
@RequestMapping(value = "/users")
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);
	private Result result = new Result();
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/save")
	public Result save(@RequestBody User userVo){
		logger.info("in usercontroller save method {}", userVo);
		try {
			result = userService.save(userVo);
		} catch(Exception e) {
			logger.error(e.getMessage());
			result.setResponseCode("");
			result.setResponseMessage(e.getMessage());
			result.setResponseStatus("FAILED");
		}
		return result;
	}
	
	@GetMapping("/getUserDetails")
	public User getUser(@RequestHeader(value = "userName") String userName, @RequestHeader(value="password") String password, @RequestHeader(value = "role") String role) {
		return userService.getUserDetails(userName, password, role);
	} 
	
	@PostMapping("/validateUser")
	public Result validateLoginDetails(@RequestBody User userVo){
		
		try {
			logger.info("user details username {} password {} ", userVo.getUserId(), userVo.getPassword());
			result = userService.validateUserDetails(userVo);
		} catch(Exception e) {
			logger.error(e.getMessage());
			result.setResponseCode("");
			result.setResponseMessage(e.getMessage());
			result.setResponseStatus("FAILED");
		}
		return result;
	}
	
	@GetMapping(value= "/secured")
	public ResponseEntity<String> secureEndPoint() {
		return ResponseEntity.ok("Secure endpoint available");
	}
}
