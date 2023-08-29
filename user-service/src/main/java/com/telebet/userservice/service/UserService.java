package com.telebet.userservice.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.telebet.userservice.entities.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	
	//simulate save operation
	public User save(User userVo) {
		userVo.setUserId(userVo.getUserId());
		userVo.setRole("USER");
		return userVo;
	}

}
