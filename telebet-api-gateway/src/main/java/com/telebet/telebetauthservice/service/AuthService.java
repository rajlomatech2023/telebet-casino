package com.telebet.telebetauthservice.service;

import org.apache.sshd.common.config.keys.loader.openssh.kdf.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.telebet.telebetauthservice.entities.AuthRequest;
import com.telebet.telebetauthservice.entities.AuthResponse;
import com.telebet.telebetauthservice.entities.UserVO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

	private RestTemplate restTemplate;
	private JwtUtil jwtUtil;
	
	public AuthResponse register(AuthRequest request) {
		
		//do validation if user exists in DB
		request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
		
		UserVO registeredUser = restTemplate.postForObject("http://user-service/users", request, UserVO.class);
		
		String acessToken = jwtUtil.generate(registeredUser.getId(), registeredUser.getRole(), "ACCESS");
		String refreshToken = jwtUtil.generate(registeredUser.getId(), registeredUser.getRole(), "REFRESH");
		
		return new AuthResponse(acessToken, refreshToken);
		
	}
}
