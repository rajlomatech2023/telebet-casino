package com.telebet.telebetauthservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telebet.telebetauthservice.entities.AuthResponse;
import com.telebet.telebetauthservice.entities.UserVO;
import com.telebet.telebetauthservice.model.Result;
import com.telebet.telebetauthservice.proxy.UserServiceProxy;

@Service
public class AuthService {
	
	Logger logger = LoggerFactory.getLogger(AuthService.class);
	
	@Autowired
    private JwtUtil jwtUtil;
	
	@Autowired
	private UserServiceProxy userServiceProxy;

	public Result register(UserVO request) {
		return userServiceProxy.save(request);
	}
	
	public AuthResponse validateLogin(UserVO request) {
		
		AuthResponse result = userServiceProxy.validateLoginDetails(request);
		
		if("1".equals(result.getResponseCode())) {
			String acessToken = jwtUtil.generate(request, "ACCESS");
			result.setAccessToken(acessToken);
		}
		
		return result;
	}
}
