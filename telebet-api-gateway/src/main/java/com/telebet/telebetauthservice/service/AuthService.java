package com.telebet.telebetauthservice.service;

import org.apache.sshd.common.config.keys.loader.openssh.kdf.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.telebet.telebetauthservice.entities.AuthRequest;
import com.telebet.telebetauthservice.entities.AuthResponse;
import com.telebet.telebetauthservice.entities.UserVO;

import lombok.AllArgsConstructor;

@Service
//@AllArgsConstructor
public class AuthService {

	//@Autowired
	//private RestTemplate restTemplate;
	
	@Autowired
    private JwtUtil jwtUtil;

	public AuthResponse register(AuthRequest request) {
		
		//do validation if user exists in DB
		request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
		
		UserVO registeredUser = new RestTemplate().postForObject("http://user-service/users", request, UserVO.class);
		
		String acessToken = jwtUtil.generate(registeredUser, "ACCESS");
		String refreshToken = jwtUtil.generate(registeredUser, "REFRESH");
		
		return new AuthResponse(acessToken, refreshToken);
		
	}
}
