package com.telebet.userservice.controller;

import org.apache.sshd.common.config.keys.loader.openssh.kdf.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telebet.userservice.entities.User;
import com.telebet.userservice.repository.UserRepository;
import com.telebet.userservice.service.UserService;
import com.telebet.userservice.util.PwdEncoderUtil;


@RestController
@RequestMapping(value = "/users")
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired(required = true)
	private UserRepository userRepository;
	
	PasswordEncoder encoder = PwdEncoderUtil.getDelegatingPasswordEncoder("bcrypt");
	
	@PostMapping("/save")
	public ResponseEntity<User> save(@RequestBody User userVo){
		logger.info("userDetails {} ", userVo);
		userVo.setPassword(encoder.encode(userVo.getPassword()));
		
		return ResponseEntity.ok(userRepository.save(userVo));
	}
	
	@GetMapping("/getUserDetails")
	public User getUser(@RequestHeader(value = "userName") String userName, @RequestHeader(value="password") String password, @RequestHeader(value = "role") String role) {
		logger.info("in userController getUserDetails method... password {} ", password);
		
		User loggedInUser = userRepository.findUserByUserName(userName);
		logger.info("logged in user details {} ", loggedInUser);
		
		String userPassword =encoder.encode(password); 
		logger.info("password after encryption {} ", userPassword);
		
		User userVo = null;
		
		Boolean matches = encoder.matches(password, loggedInUser.getPassword());
		if(matches) {
			userVo =  userRepository.findUserByUserNameAndPassword(userName, password);
		} else {
			logger.info("user enter password doesn't match with db password");
		}
		
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
