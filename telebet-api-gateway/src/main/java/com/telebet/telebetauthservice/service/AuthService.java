package com.telebet.telebetauthservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	
	public Result validateLogin(UserVO request) {
		
		Result result = userServiceProxy.validateLoginDetails(request);
		
		if("1".equals(result.getResponseCode())) {
			String acessToken = jwtUtil.generate(request, "ACCESS");
			result.setResponseMessage("token: "+acessToken);
		}
		
		return result;
	}
}
