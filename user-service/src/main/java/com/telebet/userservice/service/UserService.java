package com.telebet.userservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.telebet.userservice.entities.User;
import com.telebet.userservice.model.Result;
import com.telebet.userservice.repository.UserRepository;

@Service
public class UserService {
	
	private Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired(required = true)
	private UserRepository userRepository;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder() ;
	
	//simulate save operation
	public Result save(User userVo) {
		Result result = new Result();
		
		User loggedInUser = userRepository.findUserByUserName(userVo.getUserName());
		
		if(loggedInUser!=null) {
			result.setResponseCode("");
			result.setResponseMessage("User name already taken");
			result.setResponseStatus("FAILED");
		} else {
			userVo.setPassword(passwordEncoder.encode(userVo.getPassword()));
			User savedUser = userRepository.save(userVo);
			
			if(savedUser != null) {
				result.setResponseCode("");
				result.setResponseMessage("User Details Saved successfully");
				result.setResponseStatus("SUCCESS");
			} else {
				result.setResponseCode("");
				result.setResponseMessage("User Details not saved");
				result.setResponseStatus("FAILED");
			}
		}
		
		return result;
	}

	public User getUserDetails(String userName, String password, String role) {
		
		User userVo = null;
		boolean matches = false;
		User loggedInUser = userRepository.findUserByUserName(userName);
		
		if(loggedInUser != null) 
			matches = passwordEncoder.matches(password, loggedInUser.getPassword());
		else
			logger.info("user not available with the given username {} ", userName);
		
		if(matches) {
			userVo = new User();
			userVo.setUserId(loggedInUser.getUserId());
			userVo.setUserName(loggedInUser.getUserName());
			userVo.setRole(loggedInUser.getRole());
			userVo.setEmail(loggedInUser.getEmail());
			userVo.setPassword("");
		} else { 
			logger.info("invalid password");
		}
				
		return userVo;
	}

	public Result validateUserDetails(User userVo) {
		
		User loggedInUser = userRepository.findUserByUserName(userVo.getUserName());
		
		boolean matches = passwordEncoder.matches(userVo.getPassword(), loggedInUser.getPassword());
		
		Result result = new Result();
		
		if(!matches) { 
			result.setResponseCode("0");
			result.setResponseMessage("Invalid Username or Password");
			result.setResponseStatus("FAILED");
		} else {
			result.setResponseCode("1");
			result.setResponseMessage("User logged in successfully");
			result.setResponseStatus("SUCCESS");
		}
		
		return result;
	}

}
