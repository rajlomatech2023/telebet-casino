package com.telebet.telebetauthservice.service;

import org.apache.sshd.common.config.keys.loader.openssh.kdf.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.telebet.telebetauthservice.entities.AuthRequest;
import com.telebet.telebetauthservice.entities.AuthResponse;
import com.telebet.telebetauthservice.entities.UserVO;
import com.telebet.telebetauthservice.proxy.UserServiceProxy;

import lombok.AllArgsConstructor;

@Service
public class AuthService {

	//@Autowired
	//private RestTemplate restTemplate;
	
	Logger logger = LoggerFactory.getLogger(AuthService.class);
	
	@Autowired
    private JwtUtil jwtUtil;
	
	@Autowired
	private UserServiceProxy userServiceProxy;

	public AuthResponse register(UserVO request) {
		
		logger.info("in Auth Service register method {} ", request);
		
		//do validation if user exists in DB
		request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
		
		//UserVO registeredUser = new RestTemplate().postForObject("http://user-service/users", request, UserVO.class);
		
		userServiceProxy.save(request);
		
		String acessToken = jwtUtil.generate(request, "ACCESS");
		String refreshToken = jwtUtil.generate(request, "REFRESH");
		
		logger.info("accessToken: {} ", acessToken);
		
		return new AuthResponse(acessToken, refreshToken);
		
	}
}
