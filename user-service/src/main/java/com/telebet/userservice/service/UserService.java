package com.telebet.userservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder() ;
	
	//simulate save operation
	public String save(User userVo) {
		String message = null;
		logger.info("userDetails {} ", userVo);
		userVo.setPassword(passwordEncoder.encode(userVo.getPassword()));
		User savedUser = userRepository.save(userVo);
		
		if(savedUser != null)
			message = "User Details Saved successfully";
		else
			message = "User Details not saved";
		
		return message;
	}

	public User getUserDetails(String userName, String password, String role) {
		
		User userVo = null;
		User loggedInUser = userRepository.findUserByUserName(userName);
		logger.info("logged in user details {} ", loggedInUser);
		
		boolean matches = passwordEncoder.matches(password, loggedInUser.getPassword());
		logger.info("matches: {}"+matches);
		
		if(matches)
			userVo = loggedInUser;
		else 
			logger.info("invalid password");
		
		logger.info("userVo {} ", userVo);
				
		return userVo;
	}

	public Result validateUserDetails(@Valid User userVo) {
		
		User loggedInUser = userRepository.findUserByUserNameAndPassword(userVo.getUserName(), userVo.getPassword());
		Result result = null;
		if(loggedInUser != null) 
			result = Result.succeed("", "1", "user logged in.");
		else 
			result = Result.succeed("", "0", "Invalid Username or Password");
		
		return result;
	}
	
	
	
	
	
	

}
