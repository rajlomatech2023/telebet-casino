package com.telebet.userservice.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.telebet.userservice.entities.UserVO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	
	//simulate save operation
	public UserVO save(UserVO userVo) {
		String userId = String.valueOf(new Date().getTime());
		userVo.setId(userId);
		userVo.setRole("USER");
		return userVo;
	}

}
