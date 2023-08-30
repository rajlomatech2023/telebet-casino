package com.telebet.userservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.telebet.userservice.entities.User;
import com.telebet.userservice.exception.UserNotFoundException;
import com.telebet.userservice.model.Result;
import com.telebet.userservice.repository.UserRepository;
import com.telebet.userservice.util.PwdEncoderUtil;

import jakarta.validation.Valid;

@Service
public class UserService {
	
	private Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired(required = true)
	private UserRepository userRepository;
	
	private PasswordEncoder encoder = PwdEncoderUtil.getDelegatingPasswordEncoder("bcrypt");
	
	//simulate save operation
	public String save(User userVo) {
		String message = null;
		logger.info("userDetails {} ", userVo);
		userVo.setPassword(encoder.encode(userVo.getPassword()));
		User savedUser = userRepository.save(userVo);
		
		if(savedUser != null)
			message = "User Details Saved successfully";
		else
			message = "User Details not saved";
		
		return message;
	}

	public User getUserDetails(String userName, String password, String role) {
		logger.info("in userService getUserDetails method... password {} ", password);
		
		User loggedInUser = userRepository.findUserByUserName(userName);
		logger.info("logged in user details {} ", loggedInUser);
		
		String userPassword =encoder.encode(password); 
		logger.info("password after encryption {} ", userPassword);
		
		User userVo = null;
		
		boolean matches = encoder.matches(password, userPassword);
		if(matches)
			userVo =  userRepository.findUserByUserNameAndPassword(userName, password);
		else 
			logger.info("user enter password doesn't match with db password");
		
		if(userVo == null) 
			throw new UserNotFoundException("userNot found for the given id: "+userName);
		
		return userVo;
	}

	public Result validateUserDetails(@Valid User userVo) {
		
		User loggedInUser = userRepository.findUserByUserNameAndPassword(userVo.getUserName(), userVo.getPassword());
		Result result = null;
		if(loggedInUser != null) 
			result = Result.succeed("", "", "user logged");
		else 
			result = Result.succeed("", "", "Invalid Username or Password");
		
		return result;
	}
	
	
	
	
	
	

}
